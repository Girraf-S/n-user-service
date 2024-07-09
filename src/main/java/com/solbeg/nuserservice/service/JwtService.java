package com.solbeg.nuserservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.entity.User_;
import com.solbeg.nuserservice.model.TokenResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${jwt.key}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long validityInMilliSeconds;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    public TokenResponse generateToken(User user) {
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

    public DecodedJWT decodeJWT(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC256(jwtSecret))
                .build();
        return Optional.ofNullable(verifier.verify(token))
                .orElseThrow(() -> new RuntimeException("Invalid token " + token));
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        return decodedJWT.getSubject();
    }
    public String extractRole(String token){
        return decodeJWT(token).getClaim(User_.ROLE).asString();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return decodeJWT(token).getExpiresAt();
    }
}
