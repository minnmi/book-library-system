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

    private final UserRepository userRepository;

    private final ConfigurationService configurationService;

    private final BookService bookService;
    private final ModelMapper modelMapper;



    @Autowired
    public LoanedService(LoanedRepository loanedRepository, UserRepository userRepository, BookService bookService, ConfigurationService configurationService, ModelMapper modelMapper) {
        this.loanedRepository = loanedRepository;
        this.userRepository = userRepository;
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

    private boolean canLoan(Loaned loaned) throws Exception {
        int numCopies = loaned.getBook().getQuantity(); // num de cópias do livro
        int numLoaned = getQuantityLoaned(loaned.getBook()); // num de alugueis deste livro
        int numLoanedByUser = loanedRepository.getQuantityLoanedByUser(loaned.getUser()); // num de alugueis des te usuários

        if (loanedRepository.checkAlreadyLoan(loaned.getUser(), loaned.getBook()))
            return false;

        final var maximumNumberBooksUser = this.configurationService.getMaximumNumberBooksUser();
        final var proportionBooksStock = this.configurationService.getProportionBooksStock();
        var expr = ((numCopies - numLoaned) > proportionBooksStock * numCopies) && (numLoanedByUser < maximumNumberBooksUser);
        return expr;
    }

    public Loaned insertLoaned(Loaned object) throws Exception {
        if (!this.canLoan(object))
            throw new BusinessException("Book can't be loan!");

        final var maximumBookingPeriod = this.configurationService.getMaximumBookingPeriod();
        object.setInitialDate(LocalDateTime.now());
        object.setFinalDate(object.getInitialDate().plusDays(maximumBookingPeriod));

        return loanedRepository.save(object);
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

    public List<Loaned> findHistory(User user) {
        return this.loanedRepository.findHistoryByUser(user);
    }

    public Loaned convertDtoToEntity(LoanedDTO objectDTO) {
        return modelMapper.map(objectDTO, Loaned.class);
    }

    public LoanedDTO convertEntityToDto(Loaned object) {
        return modelMapper.map(object, LoanedDTO.class);
    }



}
