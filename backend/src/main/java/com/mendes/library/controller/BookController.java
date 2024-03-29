package com.mendes.library.controller;

import com.google.zxing.WriterException;
import com.mendes.library.criteria.BookCriteria;
import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.BookDTO.BookRequest;
import com.mendes.library.model.DTO.BookDTO.BookResponse;
import com.mendes.library.service.BookService;
import com.mendes.library.service.QRCodeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyAuthority('BOOK_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<BookResponse> findAllBooks(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                           BookCriteria criteria) {
        var page = bookService.findAllBooks(pageable, criteria);

        var content = page.getContent().stream()
                .map(bookService::convertEntityToDto)
                .toList();

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('BOOK_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public BookResponse findById(@PathVariable Long id) {
        var book = bookService.findById(id);
        return bookService.convertEntityToDto(book);
    }

    @PreAuthorize("hasAnyAuthority('BOOK_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse insertBook(@Valid @RequestBody BookRequest bookRequest) {
        log.info(" inserting a new book: {} ", bookRequest.getName());
        var bookEntity = bookService.convertDtoToEntity(bookRequest);
        var book = bookService.insertBook(bookEntity);
        return bookService.convertEntityToDto(book);
    }

    @PreAuthorize("hasAnyAuthority('BOOK_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse updateBook(@Valid @RequestBody BookRequest bookRequest, @PathVariable Long id) {
        log.info(" updating book of id: {} ", id);
        var bookEntity = bookService.convertDtoToEntity(bookRequest);
        var book = bookService.updateBook(id, bookEntity);
        return bookService.convertEntityToDto(book);
    }


    @PreAuthorize("hasAnyAuthority('BOOK_DELETE', 'ADMIN')")
    @PostMapping(value = "/{id}/book-cover/", consumes = "multipart/form-data")
    public void updateBookCover(@PathVariable("id") Long bookId,
                                @RequestParam("image") MultipartFile multipartFile) throws IOException, URISyntaxException {
       this.bookService.updateBookCover(bookId, multipartFile);
    }

    @GetMapping(value = "/{id}/book-cover/")
    public void getBookCover(@PathVariable("id") Long bookId, HttpServletResponse response) throws IOException{
        InputStream inputStream = this.bookService.getBookCover(bookId);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }
    
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Long id) {
        this.bookService.deleteBook(id);
    }

    @PreAuthorize("hasAnyAuthority('BOOK_VIEW', 'ADMIN')")
    @GetMapping(value = "/qr-code/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getBookQRCode(@PathVariable Long id) throws IOException, WriterException {
        var width = 400;
        var value = String.format("book:%d", id);
        return QRCodeService.createQRImage(value, width);
    }
}
