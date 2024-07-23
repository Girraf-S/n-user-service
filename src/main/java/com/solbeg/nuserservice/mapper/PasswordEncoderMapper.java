package com.solbeg.nuserservice.mapper;

import com.solbeg.nuserservice.annotations.CustomRule;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderMapper {
    private final PasswordEncoder passwordEncoder;

    @CustomRule
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
