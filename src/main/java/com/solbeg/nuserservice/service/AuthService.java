package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.exception.AppException;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.RegisterRequest;
import com.solbeg.nuserservice.model.TokenResponse;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.LoginRequest;
import com.solbeg.nuserservice.repository.UserRepository;
import com.solbeg.nuserservice.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final MailSenderService mailSenderService;

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest loginRequest) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        if (!userDetails.getUser().isActive())
            throw new AppException("User is not active", HttpStatus.LOCKED);

        return jwtService.generateToken(userDetails.getUser());
    }

    @Transactional
    public void subscriberRegistration(RegisterRequest registerRequest) {
        registerUser(registerRequest, Role.SUBSCRIBER, true);
    }

    @Transactional
    public void journalistRegistration(RegisterRequest registerRequest) {
        User user = registerUser(registerRequest, Role.JOURNALIST, false);

        mailSenderService.sendUserInfoToAdmin(user.getId(), user);
    }

    private User registerUser(RegisterRequest registerRequest, Role role, boolean active) {
        checkRegisterData(registerRequest);

        User user = userMapper.registerRequestToUser(registerRequest, role, active);

        userRepository.save(user);

        return user;
    }

    private void checkRegisterData(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email is exist");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }
    }
}
