package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.ActivationCode;
import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.exception.HeaderException;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.InfoResponse;
import com.solbeg.nuserservice.model.RegisterRequest;
import com.solbeg.nuserservice.model.TokenResponse;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.LoginRequest;
import com.solbeg.nuserservice.repository.ActivationCodeRepository;
import com.solbeg.nuserservice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final MailSenderService mailSenderService;

    @Value("${jwt.bearer}")
    private String bearer;
    @Value("${jwt.begin-index}")
    private int beginIndex;

    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = findUserByEmailOrThrowException(loginRequest.getEmail());
        return jwtService.generateToken(user);
    }

    @Transactional
    public InfoResponse subscriberRegistration(RegisterRequest registerRequest) {
        User user = registerUser(registerRequest, Role.SUBSCRIBER);
        ActivationCode activationCode = generateActivationCode(user.getId());
        activationCodeRepository.save(
                activationCode
        );
        mailSenderService.sendCode(activationCode.getCode(), user.getEmail());
        return new InfoResponse("An email has been sent to you with an activation code");
    }

    public InfoResponse journalistRegistration(RegisterRequest registerRequest) {
        registerUser(registerRequest, Role.JOURNALIST);
        return new InfoResponse("Wait for your details to be confirmed by the administrator");
    }

    public User getUser(HttpServletRequest request) {
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!jwt.startsWith(bearer))
            throw new HeaderException("Header should be started with 'Bearer'");
        return userRepository.findByEmail(jwtService.extractUsername(jwt.substring(beginIndex)))
                .orElseThrow(RuntimeException::new);
    }

    public void activateSubscriber(String code){
        ActivationCode activationCode = activationCodeRepository.findByCode(code);
        userRepository.activateUserById(activationCode.getUserId());
    }


    private User findUserByEmailOrThrowException(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("There is no user with this email: " + email));
    }

    private User registerUser(RegisterRequest registerRequest, Role role) {
        checkRegisterData(registerRequest);
        User user = userMapper.registerRequestToUser(registerRequest);
        user.setActive(false);
        user.setRole(role);
        userRepository.save(user);
        return user;
    }

    private ActivationCode generateActivationCode(Long userID) {
        return ActivationCode.builder()
                .userId(userID)
                .code(
                        encoder.encode(String.valueOf(System.currentTimeMillis()))
                )
                .build();
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
