package com.mendes.library.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "last_email_sent")
public class LastEmailSent {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "booking_available_date")
    private LocalDateTime bookingAvailableDate;

    @Column(name = "late_loan_date")
    private LocalDateTime lateLoanDate;

    @Column(name = "loan_read_notification_date")
    private LocalDateTime loanReadNotificationDate;

    public boolean lateLoanHasMoreThanNDays(int days) {
        return this.hasMoreThanNDays(this.lateLoanDate, days);
    }

    public boolean loanReadNotificationHasMoreThanNDays(int days) {
        return this.hasMoreThanNDays(this.loanReadNotificationDate, days);
    }

    public boolean bookingAvailableHasMoreThanNDays(int days) {
        return this.hasMoreThanNDays(this.bookingAvailableDate, days);
    }

    private boolean hasMoreThanNDays(LocalDateTime referenceDate, int days) {
        if (Objects.isNull(referenceDate))
            return true;

        return referenceDate.plusDays(days).isBefore(LocalDateTime.now()) ;
    }
}
