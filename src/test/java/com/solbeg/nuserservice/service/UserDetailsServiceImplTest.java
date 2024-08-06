package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.repository.UserRepository;
import com.solbeg.nuserservice.security.UserDetailsImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(ServiceTestBeans.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDetailsServiceImplTest {

    @Autowired
    private User[] users;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @Order(1)
    public void init() {
        userRepository.save(users[0]);
    }

    @Test
    @Order(2)
    public void loadByUsername() {
        assertEquals(((UserDetailsImpl) userDetailsService.loadUserByUsername(users[0].getEmail())).getUser(), users[0]);
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(users[1].getEmail()));
    }

    @Test
    @Order(3)
    @Transactional
    @Rollback(value = false)
    public void clear() {
        userRepository.delete(users[0]);
    }
}