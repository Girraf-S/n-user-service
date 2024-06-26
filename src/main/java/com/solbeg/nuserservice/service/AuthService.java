package com.solbeg.nuserservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.solbeg.nuserservice.controller.TokenResponse;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.entity.User_;
import com.solbeg.nuserservice.model.AuthParamsModel;
import com.solbeg.nuserservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Value("${jwt.key}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long validityInMilliSeconds;

    UserRepository userRepository;
    UserDetailsService userDetailsService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       @Qualifier("UserDetailServiceImpl") UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    public Optional<User> findUserByEmail(AuthParamsModel paramsModel) {
        return userRepository.findByEmail(paramsModel.getEmail());
    }

    public boolean correctPassword(AuthParamsModel paramsModel, User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(paramsModel.getPassword(), user.getPassword());
    }

    public TokenResponse login(User user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliSeconds);

        String token = JWT.create()
                .withSubject(user.getEmail())
                .withClaim(User_.ROLE, user.getRole().name())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC256(jwtSecret));

        return new TokenResponse(token);
    }

    public Optional<DecodedJWT> decodeJWT(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC256(jwtSecret))
                .build();
        return Optional.ofNullable(verifier.verify(token));
    }

    public boolean validToken(String token) {
        return decodeJWT(token).isPresent();
    }

}
