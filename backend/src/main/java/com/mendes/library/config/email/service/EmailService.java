package com.mendes.library.config.email.service;

import com.mendes.library.config.email.EmailDetails;

public interface EmailService {


    String sendSimpleMessage(EmailDetails details);


    String sendMessageWithAttachment(EmailDetails details);


}
