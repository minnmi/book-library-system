package com.mendes.library.task;

import com.mendes.library.service.BookingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BookingTask {
    private final BookingService bookingService;

    public BookingTask(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void removeOldBooking() {
        this.bookingService.removeOldBooking();
    }
}
