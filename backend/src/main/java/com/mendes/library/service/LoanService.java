package com.mendes.library.service;

import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.LoanedDTO.LoanRequest;
import com.mendes.library.model.DTO.LoanedDTO.LoanResponse;
import com.mendes.library.model.Loan;
import com.mendes.library.model.User;
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

    private final BookingService bookingService;

    private final UserRepository userRepository;

    private final UserService userService;
    private final ConfigurationService configurationService;

    private final BookService bookService;

    private final ModelMapper modelMapper;



    @Autowired
    public LoanService(LoanedRepository loanedRepository, BookingService bookingService, UserRepository userRepository, UserService userService, BookService bookService, ConfigurationService configurationService, ModelMapper modelMapper) {
        this.loanedRepository = loanedRepository;
        this.bookingService = bookingService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.configurationService = configurationService;
    }

    public Page<Loan> findAllLoanedBooks(Pageable pageable) {
        return loanedRepository.findAll(pageable);
    }

    public Loan findById(Long id) {
        Optional<Loan> optionalLoan = loanedRepository.findById(id);
        if (optionalLoan.isPresent()) {
            return optionalLoan.get();
        } else  {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + Loan.class.getName());
        }
    }

    public List<Loan> findLoansByUser(Long userId) {
        User user = userRepository.findById(userId).get();
        return loanedRepository.findLoanedsByUser(user.getId());
    }

    public List<Loan> findLoansByBook(Long bookId) {
        Book book = bookService.findById(bookId);
        return loanedRepository.findLoanedsByBook(book.getId());
    }

    private Integer getQuantityLoaned(Book book) {
        return loanedRepository.getQuantityLoaned(book);
    }

    public boolean hasBooking(Long userId, Long bookId, int numAvailable) {
        var optionalBooking = this.bookingService.findBookingByBookIdAndUserId(bookId, userId);
        if (optionalBooking.isEmpty())
            return false;

        var booking = optionalBooking.get();
        var order = this.bookingService.getBookingOrderByBookIdAndBookingId(bookId, booking.getId());
        return order < numAvailable;
    }

    private boolean canLoanBook(Book book, User currentUser) throws Exception {
        final var numCopies = book.getQuantity();
        final var numLoaned = getQuantityLoaned(book);
        final var numAvailable = numCopies - numLoaned;
        final var maximumNumberBooksUser = this.configurationService.getMaximumNumberBooksUser();
        final var proportionBooksStock = this.configurationService.getProportionBooksStock();

        int numLoanedByUser = loanedRepository.getQuantityLoanedByUser(currentUser);

        if (loanedRepository.checkAlreadyLoan(currentUser.getId(), book.getId()))
            return false;

        if (!this.hasBooking(currentUser.getId(), book.getId(),numAvailable))
            return false;

        var expr = (numAvailable > proportionBooksStock * numCopies) && (numLoanedByUser < maximumNumberBooksUser);
        return expr;
    }

    public Loan insertLoaned(Long bookId) throws Exception {
        var book = this.bookService.findById(bookId);
        var currentUser = this.userService.getLoggedUser();

        if (!canLoanBook(book, currentUser))
            throw new DataIntegrityViolationException("Error when loan a book");

        final var maximumBookingPeriod = this.configurationService.getMaximumLoanPeriod();

        var loan = Loan.builder()
                .book(book)
                .initialDate(LocalDateTime.now())
                .user(currentUser)
                .finalDate(LocalDateTime.now().plusDays(maximumBookingPeriod))
                .returned(0)
                .build();

        loan = this.loanedRepository.save(loan);

        this.bookingService.removeBookingByBookIdAndUserId(book.getId(), currentUser.getId());

        return loan;
    }

    public List<Loan> findLateLoansByUser(LocalDateTime localDateTime, Long userId) {
        User user = userRepository.findById(userId).get();
        final var lateLoans = loanedRepository.findLateLoansByUser(localDateTime, userId);
        return lateLoans;
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

    public Loan convertDtoToEntity(LoanRequest loanRequest) {
        return modelMapper.map(loanRequest, Loan.class);
    }

    public LoanResponse convertEntityToDto(Loan loan) {
        return modelMapper.map(loan, LoanResponse.class);
    }



}
