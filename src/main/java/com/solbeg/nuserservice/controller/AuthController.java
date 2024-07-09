package com.solbeg.nuserservice.controller;

import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.LoginRequest;
import com.solbeg.nuserservice.model.RegisterRequest;
import com.solbeg.nuserservice.model.TokenResponse;
import com.solbeg.nuserservice.model.UserResponse;
import com.solbeg.nuserservice.service.AuthService;
import com.solbeg.nuserservice.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/registration/subscriber")
    public TokenResponse subscriberRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.subscriberRegistration(registerRequest);
    }

    @PostMapping("/registration/journalist")
    public TokenResponse journalistRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
            return authService.journalistRegistration(registerRequest);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/users")
    public UserResponse getUser(HttpServletRequest request) {
        return userMapper.userToUserResponse(authService.getUser(request));
    }
}
