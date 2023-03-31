package com.mendes.library.service;

import com.mendes.library.config.email.EmailDetails;
import com.mendes.library.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

//    @Value("${mail.username}")


    private String sender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {


            SimpleMailMessage mailMessage = new SimpleMailMessage();


            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());


            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }


        catch (RuntimeException e) {
            return "Error while Sending Mail";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());

            /*
                -> (new File(pathToAttachment : "c:/Sample.jpg")
                -> mimeMessageHelper.addAttachment("CoolImage.jpg", file);
             */
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);


            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }


        catch (MessagingException e) {

            return "Error while sending mail!!!";
        }
    }
}
