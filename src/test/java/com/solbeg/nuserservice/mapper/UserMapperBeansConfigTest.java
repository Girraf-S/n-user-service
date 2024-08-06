package com.solbeg.nuserservice.mapper;

import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.entity.UserArchive;
import com.solbeg.nuserservice.model.RegisterRequest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
class UserMapperBeansConfigTest {

    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    public static long id = 1L;
    public static final String email = "bilbo.baggins@mail.shire";
    public static final String firstName = "Bilbo";
    public static final String lastName = "Baggins";
    public static final  String password = passwordEncoder().encode("Bag123");
    public static final  Role role = Role.JOURNALIST;
    public static final  Role wrongRole = Role.SUBSCRIBER;
    public static final  boolean isActive = true;
    public static final boolean isEmailVerified = false;

    @Bean
    public RegisterRequest registerRequest() {
        return RegisterRequest.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .repeatPassword(password)
                .build();
    }
    @Bean
    public User user() {
        return User.builder()
                .id(id)
                .email(email)
                .role(role)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .isActive(isActive)
                .isEmailVerified(isEmailVerified)
                .build();
    }

    @Bean
    public UserArchive userArchive() {
        return UserArchive.builder()
                .id(id)
                .role(role)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(isActive)
                .build();
    }
}