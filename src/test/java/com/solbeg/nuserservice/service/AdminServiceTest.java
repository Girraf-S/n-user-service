package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.exception.AppException;
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
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private User[] users;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @Transactional
    @Rollback(false)
    public void init() {
        userRepository.saveAll(Arrays.asList(users));
    }

    @Test
    @Order(2)
    @Transactional
    @Rollback(value = false)
    public void activateById() {
        for (UserArchiveResponse user :
                adminService.getAllUsers(false, Pageable.ofSize(10))) {
            assertTrue(userRepository.findById(user.getId()).isEmpty());
            adminService.activateUserById(user.getId());
            assertTrue(userRepository.findById(user.getId()).isPresent());
            assertThrows(AppException.class, () -> adminService.activateUserById(user.getId() + users.length));
        }
    }

    @Test
    @Order(3)
    @Transactional
    @Rollback(value = false)
    public void clear() {
        userRepository.deleteAllById(
                Arrays.stream(users).map(User::getId).toList()
        );
    }
}