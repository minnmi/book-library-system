package com.mendes.library.task;

import com.mendes.library.config.email.EmailDetails;
import com.mendes.library.model.Book;
import com.mendes.library.model.Booking;
import com.mendes.library.model.LastEmailSent;
import com.mendes.library.model.User;
import com.mendes.library.repository.LastEmailSentRepository;
import com.mendes.library.service.BookingService;
import com.mendes.library.service.EmailService;
import com.mendes.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class BookingTask {
    private final Logger logger = LoggerFactory.getLogger(BookingTask.class);
    private final BookingService bookingService;
    private final EmailService emailService;

    private final LastEmailSentRepository lastEmailSentRepository;

    public BookingTask(BookingService bookingService, UserService userService, EmailService emailService, LastEmailSentRepository lastEmailSentRepository) {
        this.bookingService = bookingService;
        this.emailService = emailService;
        this.lastEmailSentRepository = lastEmailSentRepository;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void removeOldBooking() {
        try {
            this.bookingService.removeOldBooking();
        } catch (Exception e) {
            logger.error("Error when removing old bookings: ", e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void notifyBookingAvailability() {
        List<Booking> bookings = this.bookingService.getBookings();
        for (var booking: bookings) {
            User user = booking.getUser();
            LastEmailSent lastEmailSent = user.getLastEmailSent();
            if (!lastEmailSent.bookingAvailableHasMoreThanNDays(1))
                continue;;

            if (!this.bookingService.isAvailable(booking))
                continue;

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(user.getEmail());
            emailDetails.setSubject("Read notification");
            emailDetails.setMsgBody(String.format("Your book is available: {}", booking.getBook().getName()));

            this.emailService.sendSimpleMail(emailDetails);

            lastEmailSent.setBookingAvailableDate(LocalDateTime.now());
            this.lastEmailSentRepository.save(lastEmailSent);
        }
    }
}
