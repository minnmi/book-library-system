package com.mendes.library.service;


import com.mendes.library.model.Author;
import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.BookDTO.BookDTO;
import com.mendes.library.repository.AuthorRepository;
import com.mendes.library.repository.BookRepository;
import com.mendes.library.repository.LiteratureCategoryRepository;
import com.mendes.library.repository.PublisherRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    private final AuthorRepository authorRepository;

    private final PublisherRepository publisherRepository;

    private final LiteratureCategoryRepository literatureCategoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository, PublisherRepository publisherRepository, LiteratureCategoryRepository literatureCategoryRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.literatureCategoryRepository = literatureCategoryRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + Book.class.getName());
        }
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public Book insertBook(Book object) {
        object.setId(null);
        List<Author> authors = new ArrayList<>();
        for (Author authorIt : object.getAuthors()) {
            Long authorId = authorIt.getId();
            if (authorId == null) { // Se autor não existe, crie
                Author author = this.authorRepository.save(authorIt);
                authors.add(author);
            } else {// Senão, pegue do banco
                Optional<Author> optAuthor = this.authorRepository.findById(authorId);
                if (optAuthor.isPresent()) {
                    authors.add(optAuthor.get());
                } else {
                    throw new ObjectNotFoundException("Object not found: " + optAuthor + " type " + Author.class.getName());
                }
            }
        }
        object.getAuthors().clear();
        object.getAuthors().addAll(authors);
//        final var publisher = this.publisherRepository.findById(object.getPublisher().getId()).get();
//        object.setPublisher(publisher);
//        final var literatureCategory = this.literatureCategoryRepository.findById(object.getLiteratureCategory().getId()).get();
//        object.setLiteratureCategory(literatureCategory);

        return bookRepository.save(object);
    }


    public Book updateBook(Long id, Book object) {
        if (id == null) {
            throw new IllegalArgumentException("Book can't be null.");
        }
        Book newObject = findById(id);
        toUpdateBook(newObject, object);
        return bookRepository.save(newObject);
    }

    public void deleteBook(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("Book can't be null.");
        }
        this.bookRepository.deleteById(id);
    }


    /**
     * Update object with new informations
     *
     * @param newObject
     * @param object
     */

    private void toUpdateBook(Book newObject, Book object) {
        newObject.setName(object.getName());
        newObject.setIsbn(object.getIsbn());
        if (Objects.isNull(newObject.getAuthors())) {
            newObject.setAuthors(object.getAuthors());
        } else {
            newObject.getAuthors().clear();
            newObject.getAuthors().addAll(object.getAuthors());
        }
        newObject.setPublisher(object.getPublisher());
        newObject.setLiteratureCategory(object.getLiteratureCategory());
    }

    public Book convertDtoToEntity(BookDTO objectDTO) {
        return modelMapper.map(objectDTO, Book.class);
    }

    public BookDTO convertEntityToDto(Book object) {
        return modelMapper.map(object, BookDTO.class);
    }

}
