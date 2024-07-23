package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;
    private final UserMapper userMapper;
    @Value("${server.domain}")
    private String domain;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.admin-mail}")
    private String adminMail;

    public void sendUserInfoToAdmin(String code, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String subject = "Activation code";
        String message = "Hello! To verify your email visit link '"
                + domain + "account/verify-mail/" + code +
                "'";
        mailMessage.setFrom(username);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public void sendUserInfoToAdmin(Long id, User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String subject = "Activate user with id";

        UserResponse userResponse = userMapper.userToUserResponse(user);

        String message = "User: " +
                userResponse.toString() +
                "\n" +
                "Activate user: link '" +
                domain +
                "admin/activate/" +
                id +
                "'";

        mailMessage.setFrom(user.getEmail());
        mailMessage.setTo(adminMail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
