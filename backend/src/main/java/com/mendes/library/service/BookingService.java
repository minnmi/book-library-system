package com.mendes.library.service;

import com.mendes.library.exception.ELoanAvailability;
import com.mendes.library.model.Book;
import com.mendes.library.model.Booking;
import com.mendes.library.model.DTO.BookindDTO.BookingRequest;
import com.mendes.library.model.DTO.BookindDTO.BookingResponse;
import com.mendes.library.model.User;
import com.mendes.library.repository.BookingRepository;
import com.mendes.library.repository.LoanedRepository;
import com.mendes.library.service.exception.DataIntegrityViolationException;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ConfigurationService configurationService;
    private final LoanService loanService;
    private final BookService bookService;

    public BookingService(BookingRepository bookingRepository, UserService userService, ModelMapper modelMapper, ConfigurationService configurationService, LoanedRepository loanRepository, LoanService loanService, BookService bookService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.configurationService = configurationService;
        this.loanService = loanService;
        this.bookService = bookService;
    }

    public Page<Booking> findAllBooks(Pageable pageable) {
        return this.bookingRepository.findAll(pageable);
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found: " + id + " type " + Booking.class.getName()));
    }

    public Optional<Booking> findBookingByBookIdAndUserId(Long bookId, Long userId) {
        return this.bookingRepository.findBookingByBookIdAndUserId(bookId, userId);
    }

    public int getBookingOrderByBookIdAndBookingId(Long bookId, Long BookingId) {
        return this.bookingRepository.getBookingOrderByBookIdAndBookingId(bookId, BookingId);
    }

    public Booking insertBooking(Long bookId) {
        var currentUser = this.userService.getLoggedUser();

        if (this.bookingRepository.findBookingByBookIdAndUserId(bookId, currentUser.getId()).isPresent())
            throw new DataIntegrityViolationException("Reserva já existe");

        if (this.loanService.checkAlreadyLoan(currentUser.getId(), bookId))
            throw new DataIntegrityViolationException("Livro já alugado");

        logger.info("Searching for book id: {}", bookId);
        Book book = this.bookService.findById(bookId);

        logger.info("Getting user logged");
        User user = this.userService.getLoggedUser();

        logger.info("Creating booking");
        var booking = new Booking();
        booking.setUser(user);
        booking.setBook(book);
        booking.setPriority(1);
        booking.setCreatedDate(LocalDate.now());

        logger.info("Saving booking");
        return this.bookingRepository.save(booking);
    }
    public Booking updateBooking(Long id, Booking booking) {
        Booking current = this.findById(id);
        this.toUpdateBooking(booking, current);
        return this.bookingRepository.save(current);
    }
    private void toUpdateBooking(Booking booking, Booking currentBooking) {
        currentBooking.setUser(booking.getUser());
        currentBooking.setBook(booking.getBook());
        currentBooking.setPriority(booking.getPriority());
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
        var oldBooking = this.bookingRepository.findAllBefore(before.toLocalDate());

        logger.info("{} booking found", oldBooking.size());

        this.bookingRepository.deleteAll(oldBooking);

        logger.info("booking removed");
    }

    public void deleteBooking(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("Booking can't be null");
        }
        this.bookingRepository.deleteById(id);
    }

    public Booking convertDtoToEntity(BookingRequest bookingRequest) {
        return modelMapper.map(bookingRequest, Booking.class);
    }

    public BookingResponse convertEntityToDto(Booking booking) {
        return modelMapper.map(booking, BookingResponse.class);
    }

    public List<Booking> getBookingByUser(Long userId) {
        return this.bookingRepository.findBookingByUserId(userId);
    }

    public boolean isAvailable(Booking booking) {
        try {
            return this.loanService.canLoanBook(booking.getBook(), booking.getUser()) == ELoanAvailability.OK;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public List<Booking> getBookings() {
        return this.bookingRepository.findAll();
    }
}
