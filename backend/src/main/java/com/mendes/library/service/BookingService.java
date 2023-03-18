package com.mendes.library.service;

import com.mendes.library.model.Book;
import com.mendes.library.model.Booking;
import com.mendes.library.model.DTO.BookindDTO.BookingRequest;
import com.mendes.library.model.DTO.BookindDTO.BookingResponse;
import com.mendes.library.model.User;
import com.mendes.library.repository.BookingRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ConfigurationService configurationService;

    private final BookService bookService;

    public BookingService(BookingRepository bookingRepository, UserService userService, ModelMapper modelMapper, ConfigurationService configurationService, BookService bookService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.configurationService = configurationService;
        this.bookService = bookService;
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
        logger.info("Find booking by user id {} and book id {}", userId, bookId);
        var bookingOpt = this.findBookingByBookIdAndUserId(bookId, userId);

        if (bookingOpt.isPresent()) {
            logger.info("Deleting booking id: {}", bookingOpt.get().getId());
            this.bookingRepository.deleteById(bookingOpt.get().getId());
            logger.info("Booking deleted");
        } else {
            logger.info("No booking found");
        }
    }

    public void removeOldBooking() throws Exception {
        var maxDays = this.configurationService.getBookingTimeOut();
        var before = LocalDateTime.now().minusDays(maxDays);

        logger.info("Fetching all booking before {}", before);
        var oldBooking = this.bookingRepository.findAllBefore(before);

        logger.info("{} booking found", oldBooking.size());

        this.bookingRepository.deleteAll(oldBooking);

        logger.info("booking removed");
    }

    public Booking insertBook(Long bookId) {
        logger.info("Searching for book id: {}", bookId);
        Book book = this.bookService.findById(bookId);

        logger.info("Getting user logged");
        User user = this.userService.getLoggedUser();

        logger.info("Creating booking");
        var booking = Booking.builder()
                .user(user)
                .book(book)
                .priority(1)
                .currentDate(LocalDateTime.now())
                .build();

        logger.info("Saving booking");
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
