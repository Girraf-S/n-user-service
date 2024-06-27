package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.model.TokenResponse;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.AuthParamsModel;
import com.solbeg.nuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private User findUserByEmailOrThrowException(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("There is no user with this email: " + email));
    }

    private boolean isPasswordValid(AuthParamsModel paramsModel, User user) {
        return passwordEncoder.matches(paramsModel.getPassword(), user.getPassword());
    }

    public TokenResponse login(AuthParamsModel params) {
        User user = findUserByEmailOrThrowException(params.getEmail());

        if (!isPasswordValid(params, user)) {
            throw new IllegalArgumentException("Incorrect password.");
        }
        return jwtService.createToken(user);
    }
}
