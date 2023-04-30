package com.PetFinder.Artemisa.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String toEmail,
                        String subject,
                        String body) throws EmailException{
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("team@artemisapetfinder.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Error while sending email");
        }

    }

}