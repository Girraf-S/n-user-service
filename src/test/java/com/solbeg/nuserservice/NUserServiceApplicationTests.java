package com.solbeg.nuserservice;

import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NUserServiceApplicationTests {

    @Autowired
    UserRepository userRepository;
    @Test
    void contextLoads() {
        User user = User.builder()
                .email("ks@s.s")
                .role(Role.SUBSCRIBER)
                .password("gfdgfd")
                .isActive(false)
                .firstName("Fgfd")
                .lastName("Fdsf")
                .build();
        User user1 = userRepository.save(user);
        System.out.println(user1.equals(user));
        System.out.println(user1.getId());
        System.out.println(user.getId());
   }

}
