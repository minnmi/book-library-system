package com.mendes.library.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LoanTask {
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void notifyReading() {
        // do it
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    public void notifyLoanedTimeOut() {
        // do it
    }
}
