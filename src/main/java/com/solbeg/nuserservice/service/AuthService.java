package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.model.RegisterModel;
import com.solbeg.nuserservice.model.TokenResponse;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.LoginModel;
import com.solbeg.nuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private User findUserByEmailOrThrowException(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("There is no user with this email: " + email));
    }

    private boolean isPasswordValid(LoginModel paramsModel, User user) {
        return passwordEncoder.matches(paramsModel.getPassword(), user.getPassword());
    }

    private User registerUser(RegisterModel registerModel, Role role, boolean isActive) {
        checkRegisterData(registerModel);
        User user = User.builder()
                .firstName(registerModel.getFirstName())
                .lastName(registerModel.getLastName())
                .password(passwordEncoder.encode(registerModel.getPassword()))
                .email(registerModel.getEmail())
                .role(role)
                .isActive(isActive)
                .build();
        userRepository.save(user);
        return user;
    }

    private void checkRegisterData(RegisterModel registerModel) {
        if(userRepository.findByEmail(registerModel.getEmail()).isPresent()){
            throw new IllegalArgumentException("User with this email is exist");
        }
        if(!registerModel.getPassword().equals(registerModel.getRepeatPassword())){
            throw new IllegalArgumentException("Password does not match");
        }
    }

    public TokenResponse login(LoginModel loginModel) {
        User user = findUserByEmailOrThrowException(loginModel.getEmail());

        if (!isPasswordValid(loginModel, user)) {
            throw new IllegalArgumentException("Incorrect password.");
        }
//        authenticationManager.authenticate( //todo indicate which is better
//                new UsernamePasswordAuthenticationToken(
//                        loginModel.getEmail(),
//                        loginModel.getPassword()
//                )
//        );
//        User user = findUserByEmailOrThrowException(loginModel.getEmail());
        return jwtService.generateToken(user);
    }
    public TokenResponse subscriberRegistration(RegisterModel registerModel) {
        User user = registerUser(registerModel, Role.SUBSCRIBER, true);
        return jwtService.generateToken(user);
    }

    public TokenResponse journalistRegistration(RegisterModel registerModel) {
        User user = registerUser(registerModel, Role.JOURNALIST, false);
        return jwtService.generateToken(user);
    }
}
