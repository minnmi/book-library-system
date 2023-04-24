package com.mendes.library.service;

import com.mendes.library.config.email.EmailDetails;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {


    void sendSimpleMail(EmailDetails details);


    void sendMailWithAttachment(EmailDetails details) throws MessagingException;


}
