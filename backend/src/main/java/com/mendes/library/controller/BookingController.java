package com.mendes.library.controller;

import com.mendes.library.model.DTO.BookindDTO.BookingRequest;
import com.mendes.library.model.DTO.BookindDTO.BookingResponse;
import com.mendes.library.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasAnyAuthority('BOOKING_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<BookingResponse> findAllBooking(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var temp = this.bookingService.findAllBooks(pageable);
        var content = temp.getContent().stream().map(bookingService::convertEntityToDto).toList();
        return new PageImpl<>(content, temp.getPageable(), temp.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('BOOKING_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public BookingResponse findById(@PathVariable Long id) {
        var booking = this.bookingService.findById(id);
        return this.bookingService.convertEntityToDto(booking);
    }

    @PreAuthorize("hasAnyAuthority('BOOKING_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse insertBooking(@Valid @RequestBody Long bookId) {
        var booking = this.bookingService.insertBook(bookId);
        return this.bookingService.convertEntityToDto(booking);
    }

    @PreAuthorize("hasAnyAuthority('BOOKING_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookingResponse updateBooking(@Valid @RequestBody BookingRequest request, @PathVariable Long id) {
        var booking = this.bookingService.convertDtoToEntity(request);
        booking = this.bookingService.updateBook(id, booking);
        return this.bookingService.convertEntityToDto(booking);
    }

    @PreAuthorize("hasAnyAuthority('BOOKING_DELETE', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBooking(@PathVariable Long id) {
        this.bookingService.deleteBooking(id);
    }

}
