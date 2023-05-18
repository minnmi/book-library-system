package com.mendes.library.task;

import com.mendes.library.config.email.EmailDetails;
import com.mendes.library.model.Book;
import com.mendes.library.model.LastEmailSent;
import com.mendes.library.model.Loan;
import com.mendes.library.model.User;
import com.mendes.library.repository.LastEmailSentRepository;
import com.mendes.library.service.EmailService;
import com.mendes.library.service.LoanService;
import com.mendes.library.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class LoanTask {
    private final EmailService emailService;
    private final UserService userService;
    private final LoanService loanService;

    private final LastEmailSentRepository lastEmailSentRepository;


    public LoanTask(EmailService emailService, UserService userService, LoanService loanService, LastEmailSentRepository lastEmailSentRepository) {
        this.emailService = emailService;
        this.userService = userService;
        this.loanService = loanService;
        this.lastEmailSentRepository = lastEmailSentRepository;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void notifyReading() {
        List<Loan> loans = this.loanService.findLoansNotReturned();
        for (var loan: loans) {
            User user = loan.getUser();
            LastEmailSent lastEmailSent = user.getLastEmailSent();
            if (!lastEmailSent.loanReadNotificationHasMoreThanNDays(1))
                return;

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(user.getEmail());
            emailDetails.setSubject("Read notification");
            emailDetails.setMsgBody(String.format("You time to read xD. Book: {}", loan.getBook().getName()));

            this.emailService.sendSimpleMail(emailDetails);

            lastEmailSent.setLoanReadNotificationDate(LocalDateTime.now());
            this.lastEmailSentRepository.save(lastEmailSent);
        }
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void notifyLoanedTimeOut() {
        List<Loan> loans = this.loanService.findLateLoan();
        for (var loan: loans) {
            User user = loan.getUser();

            LastEmailSent lastEmailSent = user.getLastEmailSent();
            if (!lastEmailSent.lateLoanHasMoreThanNDays(1))
                return;

            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(user.getEmail());
            emailDetails.setSubject("Late loan");

            emailDetails.setMsgBody(String.format("Your load is late (book title: {})", loan.getBook().getName()));

            this.emailService.sendSimpleMail(emailDetails);

            lastEmailSent.setLateLoanDate(LocalDateTime.now());
            this.lastEmailSentRepository.save(lastEmailSent);
        }
    }
}
