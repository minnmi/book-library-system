package com.mendes.library.repository;

import com.mendes.library.model.Book;
import com.mendes.library.model.Loan;
import com.mendes.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanedRepository extends JpaRepository<Loan, Long> {


    List<Loan> findLoanByUserId(Long id);

    List<Loan> findLoanByBook(Long id);

    @Query("SELECT count(l.id) FROM Loan l WHERE l.book = :book AND l.returned = 0")
    int getQuantityLoaned(Book book);

    @Query("SELECT l FROM Loan l where l.returned = 0 and l.finalDate < :dateNow and l.user.id = :userId")
    List<Loan> findLateLoansByUser(LocalDateTime dateNow, Optional<User> userId);

    @Query("SELECT l FROM Loan l where l.initialDate > :from and l.initialDate < :to")
    List<Loan> findByInitialDate(LocalDateTime from, LocalDateTime to);

    @Query("SELECT l FROM Loan l where l.finalDate > :from and l.finalDate < :to")
    List<Loan> findByFinalDate(LocalDateTime from, LocalDateTime to);

    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId ORDER BY l.id DESC")
    List<Loan> findHistoryByUser(Long userId);

    @Query("SELECT count(l.id) FROM Loan l WHERE l.user = :user and l.returned = 0")
    int getQuantityLoanedByUser(User user);

    @Query("SELECT CASE WHEN count(l.id) > 0 THEN true ELSE false END FROM Loan l WHERE l.user.id = :userId AND l.book.id = :bookId AND l.returned = 0")
    boolean checkAlreadyLoan(Long userId, Long bookId);

    @Query("SELECT l FROM Loan l where  l.user.id = :userId and l.returned = 0")
    List<Loan> findLoanNotReturnedByUserId(Long userId);

    @Query("select l from Loan l where l.returned = 0")
    List<Loan> findLoansNotReturned();

    @Query("select l from Loan l where l.returned = 0 and l.finalDate < :now")
    List<Loan> findLateLoan(LocalDateTime now);
}
