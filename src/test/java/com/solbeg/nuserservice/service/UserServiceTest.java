package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.UserArchiveResponse;
import com.solbeg.nuserservice.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(ServiceTestBeans.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User[] users;

    @Test
    @Order(1)
    @Transactional
    @Rollback(value = false)
    public void init(){
        userRepository.saveAll(Arrays.asList(users));
    }

    @Test
    @Order(2)
    public void findNotActive(){
        Page<UserArchiveResponse> nonActiveUsers = userService
                .findAllByActive(false, Pageable.ofSize(10));
        for (UserArchiveResponse u :
                nonActiveUsers) {
            assertTrue(userService.findById(u.getId()).isEmpty());
        }
    }

    @Test
    @Order(3)
    public void findActive(){
        Page<UserArchiveResponse> nonActiveUsers = userService
                .findAllByActive(true, Pageable.ofSize(10));
        for (UserArchiveResponse u :
                nonActiveUsers) {
            assertTrue(userService.findById(u.getId()).isPresent());
        }
    }

    @Test
    @Order(4)
    public void findActiveNullableArg(){
        Page<UserArchiveResponse> nonActiveUsers = userService
                .findAllByActive(null, Pageable.ofSize(10));
        for (UserArchiveResponse u :
                nonActiveUsers) {
            assertTrue(userService.findById(u.getId()).isPresent());
        }
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(value = false)
    public void activateById() {
        Page<UserArchiveResponse> nonActiveUsers = userService
                .findAllByActive(false, Pageable.ofSize(10));
        for (UserArchiveResponse u :
                nonActiveUsers) {
            assertTrue(userService.findById(u.getId()).isEmpty());
            userService.activateUserById(u.getId());
            assertTrue(userService.findById(u.getId()).get().isActive());
        }
    }

    @Test
    @Order(6)
    @Transactional
    @Rollback(value = false)
    public void clear(){
        userRepository.deleteAllById(
                Arrays.stream(users).map(User::getId).toList()
        );
    }
}
