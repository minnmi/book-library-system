package com.mendes.library.config.email.service;

import com.mendes.library.config.email.EmailDetails;

public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMessage(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMessageWithAttachment(EmailDetails details);


}
