package com.solbeg.nuserservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;
    @Value("${server.domain}")
    private String domain;
    @Value("${spring.mail.username}")
    private String username;
    public void sendCode(String code, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String subject = "Activation code";
        String message = "Hello! To activating account visit link '"
                + domain + "/auth/activate/subscriber/" + code +
                "'";

        mailMessage.setFrom(username);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
