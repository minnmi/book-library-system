package com.mendes.library.service;

import com.mendes.library.model.Booking;
import com.mendes.library.model.DTO.BookingRequest;
import com.mendes.library.model.DTO.BookingResponse;
import com.mendes.library.model.User;
import com.mendes.library.repository.BookingRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public BookingService(BookingRepository bookingRepository, UserService userService, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public Page<Booking> findAllBooks(Pageable pageable) {
        return this.bookingRepository.findAll(pageable);
    }

    public Booking findById(Long id) {
        var bookingOpt = this.bookingRepository.findById(id);
        if (bookingOpt.isEmpty())
            throw new ObjectNotFoundException("");

        return bookingOpt.get();
    }

    public Optional<Booking> findBookingByBookIdAndUserId(Long bookId, Long userId) {
        return this.bookingRepository.findBookingByBookIdAndUserId(bookId, userId);
    }

    public int getBookingOrderByBookIdAndBookingId(Long bookId, Long BookingId) {
        return this.bookingRepository.getBookingOrderByBookIdAndBookingId(bookId, BookingId);
    }

    public void removeBookingByBookIdAndUserId(Long bookId, Long userId) {
        var bookingOpt = this.findBookingByBookIdAndUserId(bookId, userId);
        if (bookingOpt.isPresent())
            this.bookingRepository.deleteById(bookingOpt.get().getId());
    }

    public void removeOldBooking() {

    }


    public Booking insertBook(Booking booking) {
        User user = this.userService.getLoggedUser();
        booking.setUser(user);
        return this.bookingRepository.save(booking);
    }


    public Booking updateBook(Long id, Booking booking) {
        var current = this.findById(id);
        this.toUpdateBooking(booking, current);
        current = this.bookingRepository.save(current);
        return current;
    }

    public void deleteBooking(Long id) {
        this.bookingRepository.deleteById(id);
    }

    private void toUpdateBooking(Booking updated, Booking current) {
        current.setBook(updated.getBook());
        current.setPriority(updated.getPriority());
    }

    public Booking convertDtoToEntity(BookingRequest request) {
        return modelMapper.map(request, Booking.class);
    }

    public BookingResponse convertEntityToDto(Booking model) {
        return modelMapper.map(model, BookingResponse.class);
    }

}
