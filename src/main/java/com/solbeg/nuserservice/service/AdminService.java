package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.exception.AppException;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.UserResponse;
import com.solbeg.nuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public Page<UserResponse> getAllUsers(Boolean isActive, Pageable pageable) {
        if (isActive == null) {
            return userRepository.findAll(pageable)
                    .map(userMapper::userToUserResponse);
        }
        return userRepository.findAllByActive(isActive, pageable)
                .map(userMapper::userToUserResponse);
    }

    public void activateUserById(Long id) {
        userRepository.findById(id).ifPresentOrElse(
                user -> userRepository.activateUserById(id),
                () -> {
                    throw new AppException("Not found user with id = " + id, HttpStatus.NOT_FOUND);
                }
        );
    }
}
