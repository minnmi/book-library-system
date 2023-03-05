package com.mendes.library.service;

import com.mendes.library.model.Author;
import com.mendes.library.model.DTO.AuthorDTO.AuthorDTO;
import com.mendes.library.repository.AuthorRepository;
import com.mendes.library.repository.BookRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final ModelMapper modelMapper;

    private final BookRepository bookRepository;


    @Autowired
    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    public Page<Author> findAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public Author findById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get();
        } else {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + Author.class.getName());
        }
    }

    public Author insertAuthor(Author object) {
        object.setId(null);
        return authorRepository.save(object);
    }

    public Author updateAuthor(Long id, Author object) {
        if (id == null) {
            throw new IllegalArgumentException("Author can't be null.");
        }
        Author newObject = findById(id);
        toUpdateAuthor(newObject, object);
        return authorRepository.save(newObject);
    }

    public void deleteAuthor(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("Author can't be null.");
        }
        this.authorRepository.deleteById(id);
    }

    /**
     * Update object with new informations
     *
     * @param newObject
     * @param object
     */

    private void toUpdateAuthor(Author newObject, Author object) {
        newObject.setName(object.getName());
        if (Objects.isNull(newObject.getBooks())) {
            newObject.setBooks(object.getBooks());
        } else {
            newObject.getBooks().clear();
            newObject.getBooks().addAll(object.getBooks());
        }
    }


    public Author convertDtoToEntity(AuthorDTO objectDTO) {
        return modelMapper.map(objectDTO, Author.class);
    }

    public AuthorDTO convertEntityToDto(Author object) {
        return modelMapper.map(object, AuthorDTO.class);
    }
}
