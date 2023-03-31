package com.mendes.library.service;


import com.mendes.library.model.Author;
import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.BookDTO.BookRequest;
import com.mendes.library.model.DTO.BookDTO.BookResponse;
import com.mendes.library.repository.AuthorRepository;
import com.mendes.library.repository.BookRepository;
import com.mendes.library.repository.LiteratureCategoryRepository;
import com.mendes.library.repository.PublisherRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final ModelMapper modelMapper;

    private final AuthorRepository authorRepository;

    private final PublisherRepository publisherRepository;

    private final LiteratureCategoryRepository literatureCategoryRepository;

    private final StorageService storageService;

    @Autowired
    public BookService(BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository, PublisherRepository publisherRepository, LiteratureCategoryRepository literatureCategoryRepository, StorageService storageService) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.literatureCategoryRepository = literatureCategoryRepository;
        this.storageService = storageService;
    }

    public Page<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found: " + id + " type " + Book.class.getName()));
    }
    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    public InputStream getBookCover(Long bookId) throws IOException {
        Book book = this.findById(bookId);
        return this.storageService.readFile(book.getBookCover());
    }
    public Book insertBook(Book book) {
        book.setId(null);
        List<Author> authors = new ArrayList<>();
        for (Author authorIt : book.getAuthors()) {
            Long authorId = authorIt.getId();
            if (authorId == null) { // Se autor não existe, crie
                Author author = this.authorRepository.save(authorIt);
                authors.add(author);
            } else {// Senão, pegue do banco
                Optional<Author> optionalAuthor = this.authorRepository.findById(authorId);
                if (optionalAuthor.isPresent()) {
                    authors.add(optionalAuthor.get());
                } else {
                    throw new ObjectNotFoundException("Object not found: " + optionalAuthor + " type " + Author.class.getName());
                }
            }
        }
        book.getAuthors().clear();
        book.getAuthors().addAll(authors);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        if (id == null) {
            throw new IllegalArgumentException("Book can't be null.");
        }
        Book currentBook = findById(id);
        toUpdateBook(currentBook, book);
        return bookRepository.save(currentBook);
    }

    private void toUpdateBook(Book currentBook, Book book) {
        currentBook.setName(book.getName());
        currentBook.setIsbn(book.getIsbn());
        currentBook.getAuthors().clear();
        currentBook.getAuthors().addAll(book.getAuthors());
        currentBook.setPublisher(book.getPublisher());
        currentBook.setQuantity(book.getQuantity());
        currentBook.setLiteratureCategory(book.getLiteratureCategory());
    }
    public void updateBookCover(Long bookId, MultipartFile file) throws IOException, URISyntaxException {
        Book book = this.findById(bookId);

        if (StringUtils.isNotBlank(book.getBookCover())) {
            this.storageService.deleteFile(book.getBookCover());
        }

        String pathToFile = this.storageService.storeFile(file);
        book.setBookCover(pathToFile);
        this.bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("Book can't be null.");
        }
        this.bookRepository.deleteById(id);
    }

    public Book convertDtoToEntity(BookRequest bookRequest) {
        return modelMapper.map(bookRequest, Book.class);
    }
    public BookResponse convertEntityToDto(Book book) {
        return modelMapper.map(book, BookResponse.class);
    }
}
