package com.mendes.library.service;

import com.mendes.library.model.Author;
import com.mendes.library.model.DTO.AuthorDTO.AuthorResponse;
import com.mendes.library.repository.AuthorRepository;
import com.mendes.library.repository.BookRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        } else {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + Author.class.getName());
        }
    }

    public Author insertAuthor(Author author) {
        author.setId(null);
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author author) {
        if (id == null) {
            throw new IllegalArgumentException("Author can't be null.");
        }
        Author currentAuthor = findById(id);
        toUpdateAuthor(currentAuthor, author);
        return authorRepository.save(currentAuthor);
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
     * @param currentAuthor
     * @param author
     */

    private void toUpdateAuthor(Author currentAuthor, Author author) {
        currentAuthor.setName(author.getName());
    }


    public Author convertDtoToEntity(AuthorResponse authorResponse) {
        return modelMapper.map(authorResponse, Author.class);
    }

    public AuthorResponse convertEntityToDto(Author author) {
        return modelMapper.map(author, AuthorResponse.class);
    }
}
