package com.solbeg.nuserservice.controller;

import com.solbeg.nuserservice.model.LoginModel;
import com.solbeg.nuserservice.model.RegisterModel;
import com.solbeg.nuserservice.model.TokenResponse;
import com.solbeg.nuserservice.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginModel loginModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Incorrect email or password format");
        } else
            return authService.login(loginModel);
    }

    @PostMapping("/registration/sub")
    public TokenResponse subscriberRegistration(@Valid @RequestBody RegisterModel registerModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException("Incorrect registration data");
        }else
            return authService.subscriberRegistration(registerModel);
    }

    @PostMapping("/registration/journ")
    public TokenResponse journalistRegistration(@Valid @RequestBody RegisterModel registerModel, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException("Incorrect registration data");
        }else
            return authService.journalistRegistration(registerModel);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
