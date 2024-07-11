package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.exception.HeaderException;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.RegisterRequest;
import com.solbeg.nuserservice.model.TokenResponse;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.LoginRequest;
import com.solbeg.nuserservice.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Value("${jwt.bearer}")
    private String bearer;
    @Value("${jwt.begin-index}")
    private int beginIndex;

    private User findUserByEmailOrThrowException(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("There is no user with this email: " + email));
    }

    private User registerUser(RegisterRequest registerRequest, Role role, boolean isActive) {
        checkRegisterData(registerRequest);
        User user = userMapper.registerRequestToUser(registerRequest);
        user.setActive(isActive);
        user.setRole(role);
        userRepository.save(user);
        return user;
    }

    private void checkRegisterData(RegisterRequest registerRequest) {
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new IllegalArgumentException("User with this email is exist");
        }
        if(!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())){
            throw new IllegalArgumentException("Password does not match");
        }
    }

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
    public TokenResponse subscriberRegistration(RegisterRequest registerRequest) {
        User user = registerUser(registerRequest, Role.SUBSCRIBER, true);
        return jwtService.generateToken(user);
    }

    public TokenResponse journalistRegistration(RegisterRequest registerRequest) {
        User user = registerUser(registerRequest, Role.JOURNALIST, false);
        return jwtService.generateToken(user);
    }
    public User getUser(HttpServletRequest request){
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(!jwt.startsWith(bearer)) throw new HeaderException("Header should be started with 'Bearer'");
        return userRepository.findByEmail(jwtService.extractUsername(jwt.substring(beginIndex)))
                .orElseThrow(RuntimeException::new);
    }
}
