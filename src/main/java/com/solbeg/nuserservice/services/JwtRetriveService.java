package com.solbeg.nuserservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.solbeg.nuserservice.models.AuthParamsModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtRetriveService {
    @Value("${token.signing.key}")
    private String jwtSecret;
    public String retrieve(AuthParamsModel params){
        return JWT.create()
                .withClaim("email", params.getEmail())
                .withClaim("password", params.getPassword())
                .sign(Algorithm.HMAC256(jwtSecret));
    }
}
