package com.mendes.library.service;

import com.mendes.library.config.email.EmailDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceImplTest {
    @Autowired
    EmailService emailService;

    @Test
    void sendEmail() {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient("miriammendes094@gmail.com ");
        emailDetails.setSubject("Do seu futuro marido ;)");
        emailDetails.setMsgBody("Testando serviço de email");

        try {
            emailService.sendSimpleMail(emailDetails);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}