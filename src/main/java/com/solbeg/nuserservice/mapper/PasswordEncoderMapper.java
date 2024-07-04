package com.solbeg.nuserservice.mapper;

import com.solbeg.nuserservice.annotations.CustomRule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordEncoderMapper {
    @Autowired
    PasswordEncoder passwordEncoder;

    @CustomRule
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
