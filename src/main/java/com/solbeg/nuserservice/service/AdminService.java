package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.exception.AppException;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final UserMapper userMapper;
    public Page<UserResponse> getAllUsers(Boolean isActive, Pageable pageable) {
        if (isActive == null) {
            return userService.findAll(pageable)
                    .map(userMapper::userToUserResponse);
        }
        return userService.findAllByActive(isActive, pageable)
                .map(userMapper::userToUserResponse);
    }

    public void activateUserById(Long id) {
        userService.findNonActiveById(id).ifPresentOrElse(
                user -> userService.activateUserById(id),
                () -> {
                    throw new AppException("Not found user with id = " + id, HttpStatus.NOT_FOUND);
                }
        );
    }
}
