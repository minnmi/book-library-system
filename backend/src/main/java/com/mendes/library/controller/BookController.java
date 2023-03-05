package com.mendes.library.controller;

import com.google.zxing.WriterException;
import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.BookDTO.BookDTO;
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
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyAuthority('BOOK_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<BookDTO> findAllBooks(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var page = bookService.findAllBooks(pageable);
        var content = page.getContent().stream()
                .map(object -> bookService.convertEntityToDto(object))
                .collect(Collectors.toList());

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('BOOK_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public BookDTO findById(@PathVariable Long id) {
        Book object = bookService.findById(id);
        return bookService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('BOOK_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO insertBook(@Valid @RequestBody BookDTO objectDTO) {
        log.info(" inserting a new book: {} ", objectDTO.getName());
        Book objectRequest = bookService.convertDtoToEntity(objectDTO);
        Book object = bookService.insertBook(objectRequest);
        return bookService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('BOOK_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO updateBook(@Valid @RequestBody BookDTO objectDTO, @PathVariable Long id) {
        log.info(" updating book of id: {} ", id);
        Book objectRequest = bookService.convertDtoToEntity(objectDTO);
        Book object = bookService.updateBook(id, objectRequest);
        return bookService.convertEntityToDto(object);
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
        bookService.deleteBook(id);
    }

    @PreAuthorize("hasAnyAuthority('BOOK_VIEW', 'ADMIN')")
    @GetMapping(value = "/qr-code/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getBookQRCode(@PathVariable Long id) throws IOException, WriterException {
        var width = 400;
        var value = String.format("book:%d", id);
        return QRCodeService.createQRImage(value, width);
    }
}
