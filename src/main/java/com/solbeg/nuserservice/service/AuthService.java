package com.solbeg.nuserservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.solbeg.nuserservice.controller.TokenResponse;

import com.solbeg.nuserservice.entity.User_;
import com.solbeg.nuserservice.model.AuthParamsModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Value("${token.signing.key}")
    private String jwtSecret;

    public TokenResponse login(AuthParamsModel params){
        final String token = JWT.create()
                .withClaim(User_.EMAIL, params.getEmail())
                .sign(Algorithm.HMAC256(jwtSecret));
        return new TokenResponse(token);
    }
}
