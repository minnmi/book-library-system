package com.mendes.library.repository;

import com.mendes.library.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b from Booking b where b.book.id = :bookId and b.user.id = :userId")
    Optional<Booking> findBookingByBookIdAndUserId(Long bookId, Long userId);

    @Query("select count(b.id) from Booking b " +
            "where b.book.id = :bookId and b.createdDate >= (select bb.createdDate from Booking bb where bb.id = :bookingId)")
    int getBookingOrderByBookIdAndBookingId(Long bookId, Long bookingId);

    @Query("select b from Booking b where b.createdDate < :datetime")
    List<Booking> findAllBefore(LocalDate datetime);

    @Query("select b from Booking  b where b.user.id = :userId")
    List<Booking> findBookingByUserId(Long userId);
}
