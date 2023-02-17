package com.mendes.library.controller;

import com.google.zxing.WriterException;
import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.BookDTO.BookDTO;
import com.mendes.library.service.BookService;
import com.mendes.library.service.QRCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/find/all")
    public List<BookDTO> findAllBooks() {
        return bookService.findAllBooks()
                .stream()
                .map(object -> bookService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @GetMapping("/find/{id}")
    public BookDTO findById(@PathVariable Long id) {
        Book object = bookService.findById(id);
        return bookService.convertEntityToDto(object);
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO insertBook(@Valid @RequestBody BookDTO objectDTO) {
        log.info(" inserting a new book: {} ", objectDTO.getName());
        Book objectRequest = bookService.convertDtoToEntity(objectDTO);
        Book object = bookService.insertBook(objectRequest);
        return bookService.convertEntityToDto(object);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO updateBook(@Valid @RequestBody BookDTO objectDTO, @PathVariable Long id) {
        log.info(" updating book of id: {} ", id);
        Book objectRequest = bookService.convertDtoToEntity(objectDTO);
        Book object = bookService.updateBook(id, objectRequest);
        return bookService.convertEntityToDto(object);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping(value = "/qr-code/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getBookQRCode(@PathVariable Long id) throws IOException, WriterException {
        var width = 400;
        var value = String.format("book:%d", id);
        return QRCodeService.createQRImage(value, width);
    }
}
