package com.mendes.library.service;


import com.mendes.library.model.Author;
import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.BookDTO;
import com.mendes.library.model.Publisher;
import com.mendes.library.repository.AuthorRepository;
import com.mendes.library.repository.BookRepository;
import com.mendes.library.repository.PublisherRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    private final AuthorRepository authorRepository;

    private final PublisherRepository publisherRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
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

        for (Author author : object.getAuthors()) {
            Author a = authorRepository.findById(author.getId()).get();

            authors.add(a);
        }

        object.setAuthors(authors);

        final var publisher = this.publisherRepository.findById(object.getPublisher().getId()).get();
        object.setPublisher(publisher);

        return bookRepository.save(object);
    }


    public Book updateBook(Long id, Book object) {
        if (object == null || object.getId() == null) {
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
        newObject.setAuthors(object.getAuthors());
    }

    public Book convertDtoToEntity(BookDTO objectDTO) {
        return modelMapper.map(objectDTO, Book.class);
    }

    public BookDTO convertEntityToDto(Book object) {
        return modelMapper.map(object, BookDTO.class);
    }

}
