package com.mendes.library.config.email.service;

import com.mendes.library.config.email.EmailDetails;

public interface EmailService {


    String sendSimpleMail(EmailDetails details);


    String sendMailWithAttachment(EmailDetails details);


}
