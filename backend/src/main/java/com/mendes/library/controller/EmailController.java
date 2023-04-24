package com.mendes.library.controller;

import com.mendes.library.config.email.EmailDetails;
import com.mendes.library.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendMail")
    public void sendMail(@RequestBody EmailDetails details)
    {
        emailService.sendSimpleMail(details);
    }


    @PostMapping("/sendMailWithAttachment")
    public void sendMailWithAttachment(
            @RequestBody EmailDetails details) throws MessagingException {
        emailService.sendMailWithAttachment(details);
    }


}
