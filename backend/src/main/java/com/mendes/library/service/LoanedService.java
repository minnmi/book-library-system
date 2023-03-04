package com.mendes.library.service;

import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.LoanedDTO.LoanedDTO;
import com.mendes.library.model.Loaned;
import com.mendes.library.model.User;
import com.mendes.library.repository.LoanedRepository;
import com.mendes.library.repository.UserRepository;
import com.mendes.library.service.exception.BusinessException;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanedService {
    private final LoanedRepository loanedRepository;

    private final BookingService bookingService;

    private final UserRepository userRepository;

    private final UserService userService;
    private final ConfigurationService configurationService;

    private final BookService bookService;

    private final ModelMapper modelMapper;



    @Autowired
    public LoanedService(LoanedRepository loanedRepository, BookingService bookingService, UserRepository userRepository, UserService userService, BookService bookService, ConfigurationService configurationService, ModelMapper modelMapper) {
        this.loanedRepository = loanedRepository;
        this.bookingService = bookingService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.configurationService = configurationService;
    }

    public List<Loaned> findAllLoanedBooks() {
        return loanedRepository.findAll();
    }

    public Loaned findById(Long id) {
        Optional<Loaned> loaned = loanedRepository.findById(id);
        if (loaned.isPresent()) {
            return loaned.get();
        } else  {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + Loaned.class.getName());
        }
    }

    public List<Loaned> findLoansByUser(Long userId) {
        User object = userRepository.findById(userId).get();
        return loanedRepository.findLoanedsByUser(object.getId());
    }

    public List<Loaned> findLoansByBook(Long bookId) {
        Book object = bookService.findById(bookId);
        return loanedRepository.findLoanedsByBook(object.getId());
    }

    private Integer getQuantityLoaned(Book book) {
        return loanedRepository.getQuantityLoaned(book);
    }

    public boolean hasBooking(Long userId, Long bookId, int numAvailable) {
        var bookingOpt = this.bookingService.findBookingByBookIdAndUserId(bookId, userId);
        if (bookingOpt.isEmpty())
            return false;

        var booking = bookingOpt.get();
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

    public Loaned insertLoaned(Long bookId) throws Exception {
        var book = this.bookService.findById(bookId);
        var currentUser = this.userService.getLoggedUser();

        if (!canLoanBook(book, currentUser))
            throw new BusinessException("Error when loan a book");

        final var maximumBookingPeriod = this.configurationService.getMaximumBookingPeriod();

        var loan = Loaned.builder()
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

    public List<Loaned> findLateLoansByUser(LocalDateTime localDateTime, Long userId) {
        User user = userRepository.findById(userId).get();
        final var lateLoans = loanedRepository.findLateLoansByUser(localDateTime, userId);
        return lateLoans;
    }

    public List<Loaned> findByInitialDate(LocalDateTime from, LocalDateTime to) {
        return this.loanedRepository.findByInitialDate(from, to);
    }

    public List<Loaned> findByFinalDate(LocalDateTime from, LocalDateTime to) {
        return this.loanedRepository.findByFinalDate(from, to);
    }

    public List<Loaned> findHistory(Long userId) {
        return this.loanedRepository.findHistoryByUser(userId);
    }

    public Loaned convertDtoToEntity(LoanedDTO objectDTO) {
        return modelMapper.map(objectDTO, Loaned.class);
    }

    public LoanedDTO convertEntityToDto(Loaned object) {
        return modelMapper.map(object, LoanedDTO.class);
    }



}
