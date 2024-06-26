package com.solbeg.nuserservice.controller;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.AuthParamsModel;
import com.solbeg.nuserservice.service.AuthService;
import com.sun.source.tree.OpensTree;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody AuthParamsModel params) {
        Optional<User> optionalUser = authService.findUserByEmail(params);
        if (optionalUser.isPresent()) {
            if (authService.correctPassword(params, optionalUser.get())) {
                return authService.login(optionalUser.get());
            }
            return new TokenResponse("Incorrect password.");
        }
        return new TokenResponse("There is no user with this email: " + params.getEmail());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
