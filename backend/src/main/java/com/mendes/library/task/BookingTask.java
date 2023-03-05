package com.mendes.library.task;

import com.mendes.library.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BookingTask {
    private final Logger logger = LoggerFactory.getLogger(BookingTask.class);
    private final BookingService bookingService;

    public BookingTask(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void removeOldBooking() {
        try {
            this.bookingService.removeOldBooking();
        } catch (Exception e) {
            logger.error("Error when removing old bookings: ", e.getMessage());
        }
    }
}
