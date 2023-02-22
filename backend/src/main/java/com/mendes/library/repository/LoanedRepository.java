package com.mendes.library.repository;

import com.mendes.library.model.Book;
import com.mendes.library.model.Loaned;
import com.mendes.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanedRepository extends JpaRepository<Loaned, Long> {


    List<Loaned> findLoanedsByUser(Long id);

    List<Loaned> findLoanedsByBook(Long id);

    @Query("SELECT count(l.id) FROM Loaned l WHERE l.book = :book AND l.returned = 0")
    public int getQuantityLoaned(Book book);

    @Query("SELECT l FROM Loaned l where l.returned = 0 and l.finalDate < :dateNow and l.user.id = :userId")
    List<Loaned> findLateLoansByUser(LocalDateTime dateNow, Long userId);

    @Query("SELECT l FROM Loaned l where l.initialDate > :from and l.initialDate < :to")
    List<Loaned> findByInitialDate(LocalDateTime from, LocalDateTime to);

    @Query("SELECT l FROM Loaned l where l.finalDate > :from and l.finalDate < :to")
    List<Loaned> findByFinalDate(LocalDateTime from, LocalDateTime to);

    @Query("SELECT l FROM Loaned l WHERE l.user.id = :userId ORDER BY l.id DESC")
    List<Loaned> findHistoryByUser(Long userId);

    @Query("SELECT count(l.id) FROM Loaned l WHERE l.user = :user and l.returned = 0")
    public int getQuantityLoanedByUser(User user);

    @Query("SELECT CASE WHEN count(l.id) > 0 THEN true ELSE false END FROM Loaned l WHERE l.user.id = :userId AND l.book.id = :bookId AND l.returned = 0")
    public boolean checkAlreadyLoan(Long userId, Long bookId);
}
