package com.mendes.library.service;

import com.mendes.library.model.Book;
import com.mendes.library.model.Booking;
import com.mendes.library.model.DTO.LoanedDTO.LoanRequest;
import com.mendes.library.model.DTO.LoanedDTO.LoanResponse;
import com.mendes.library.model.Loan;
import com.mendes.library.model.User;
import com.mendes.library.repository.BookingRepository;
import com.mendes.library.repository.LoanedRepository;
import com.mendes.library.repository.UserRepository;
import com.mendes.library.service.exception.DataIntegrityViolationException;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanedRepository loanedRepository;

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    private final ConfigurationService configurationService;

    private final BookService bookService;

    private final ModelMapper modelMapper;


    @Autowired
    public LoanService(LoanedRepository loanedRepository, BookingRepository bookingRepository, UserRepository userRepository, UserService userService, BookService bookService, ConfigurationService configurationService, ModelMapper modelMapper) {
        this.loanedRepository = loanedRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.configurationService = configurationService;
    }

    public Page<Loan> findAllLoanedBooks(Pageable pageable) {
        return loanedRepository.findAll(pageable);
    }

    public List<Loan> findLoansByUser(Long userId) {
        return loanedRepository.findLoanByUserId(userId);
    }

    public List<Loan> findLoansNotReturnedByUser(LocalDateTime localDateTime, Long userId) {
        var user = userRepository.findById(userId);
        return loanedRepository.findLateLoansByUser(localDateTime, user);
    }

    public List<Loan> findByInitialDate(LocalDateTime from, LocalDateTime to) {
        return this.loanedRepository.findByInitialDate(from, to);
    }

    public List<Loan> findByFinalDate(LocalDateTime from, LocalDateTime to) {
        return this.loanedRepository.findByFinalDate(from, to);
    }

    public List<Loan> findHistory(Long userId) {
        return this.loanedRepository.findHistoryByUser(userId);
    }

    public List<Loan> findLoansByBook(Long bookId) {
        return loanedRepository.findLoanByBook(bookId);
    }

    public Loan findById(Long id) {
        return loanedRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found: " + id + " type " + Loan.class.getName()));
    }

    private Integer getQuantityLoaned(Book book) {
        return loanedRepository.getQuantityLoaned(book);
    }

    public boolean hasBooking(Long userId, Long bookId, int numAvailable) {
        var optionalBooking = this.bookingRepository.findBookingByBookIdAndUserId(bookId, userId);
        if (optionalBooking.isEmpty())
            return false;

        var booking = optionalBooking.get();
        var order = this.bookingRepository.getBookingOrderByBookIdAndBookingId(bookId, booking.getId());
        return order < numAvailable;
    }

    public boolean canLoanBook(Book book, User currentUser) throws Exception {
        final var numCopies = book.getQuantity();
        final var numLoaned = getQuantityLoaned(book);
        final var numAvailable = numCopies - numLoaned;
        final var maximumNumberBooksUser = this.configurationService.getMaximumNumberBooksUser();
        final var proportionBooksStock = this.configurationService.getProportionBooksStock();

        int numLoanedByUser = loanedRepository.getQuantityLoanedByUser(currentUser);

        if (checkAlreadyLoan(currentUser.getId(), book.getId()))
            return false;

        if (!this.hasBooking(currentUser.getId(), book.getId(),numAvailable))
            return false;

        return (numAvailable > proportionBooksStock * numCopies) && (numLoanedByUser < maximumNumberBooksUser);
    }

    public boolean checkAlreadyLoan(Long userId, Long bookId) {
        return loanedRepository.checkAlreadyLoan(userId, bookId);
    }

    public Loan insertLoaned(Long bookId) throws Exception {
        var book = this.bookService.findById(bookId);
        var currentUser = this.userService.getLoggedUser();

        if (!canLoanBook(book, currentUser))
            throw new DataIntegrityViolationException("Error when loan a book");

        final var maximumBookingPeriod = this.configurationService.getMaximumLoanPeriod();

        var loan = new Loan();
        loan.setBook(book);
        loan.setInitialDate(LocalDateTime.now());
        loan.setUser(currentUser);
        loan.setFinalDate(LocalDateTime.now().plusDays(maximumBookingPeriod));
        loan.setReturned(0);

        loan = this.loanedRepository.save(loan);

        Optional<Booking> bookingOpt = this.bookingRepository.findBookingByBookIdAndUserId(book.getId(), currentUser.getId());
        if (bookingOpt.isPresent()) {
            this.bookingRepository.deleteById(bookingOpt.get().getId());
        }

        return loan;
    }

    public Loan returnBook(Long loanId) {
        var loan = this.findById(loanId);
        loan.setReturned(1);
        loan.setReturnedDate(LocalDateTime.now());
        this.loanedRepository.save(loan);
        return loan;
    }

    public Loan convertDtoToEntity(LoanRequest loanRequest) {
        return modelMapper.map(loanRequest, Loan.class);
    }

    public LoanResponse convertEntityToDto(Loan loan) {
        return modelMapper.map(loan, LoanResponse.class);
    }

    public List<Loan> findLoansNotReturnedByUser(Long userId) {
        return this.loanedRepository.findLoanNotReturnedByUserId(userId);
    }

    public List<Loan> findLoansNotReturned() {
        return this.loanedRepository.findLoansNotReturned();
    }

    public List<Loan> findLateLoan() {
        return this.loanedRepository.findLateLoan(LocalDateTime.now());
    }
}
