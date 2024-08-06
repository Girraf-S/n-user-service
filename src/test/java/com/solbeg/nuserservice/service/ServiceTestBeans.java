package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestBeans {
    @Bean
    public User[] users(){
        return new User[]{
                User.builder()
                        .firstName("Frodo")
                        .lastName("Baggins")
                        .email("frodo@bag.shire")
                        .role(Role.SUBSCRIBER)
                        .password("$2a$12$Y/jXrPZ/aD31VQopyhPcX..6y/GzU9nav6I4OMgAHeBWjIu5NwJma")
                        .isEmailVerified(false)
                        .isActive(true)
                        .build(),
                User.builder()
                        .firstName("Bilbo")
                        .lastName("Baggins")
                        .email("bilbo@bag.shire")
                        .role(Role.JOURNALIST)
                        .password("$2a$12$Y/jXrPZ/aD31VQopyhPcX..6y/GzU9nav6I4OMgAHeBWjIu5NwJma")
                        .isEmailVerified(true)
                        .isActive(true)
                        .build(),
                User.builder()
                        .firstName("Hendalf")
                        .lastName("Grey")
                        .email("hendalf@grey.shire")
                        .role(Role.JOURNALIST)
                        .password("$2a$12$Y/jXrPZ/aD31VQopyhPcX..6y/GzU9nav6I4OMgAHeBWjIu5NwJma")
                        .isEmailVerified(false)
                        .isActive(false)
                        .build(),
                User.builder()
                        .firstName("Sauron")
                        .lastName("Lord")
                        .email("mordor@mordor.mdr")
                        .role(Role.JOURNALIST)
                        .password("$2a$12$Y/jXrPZ/aD31VQopyhPcX..6y/GzU9nav6I4OMgAHeBWjIu5NwJma")
                        .isEmailVerified(false)
                        .isActive(true)
                        .build(),
                User.builder()
                        .firstName("Aragorn")
                        .lastName("Elesar")
                        .email("gondor@gondor.gdr")
                        .role(Role.JOURNALIST)
                        .password("$2a$12$Y/jXrPZ/aD31VQopyhPcX..6y/GzU9nav6I4OMgAHeBWjIu5NwJma")
                        .isEmailVerified(false)
                        .isActive(false)
                        .build()
        };
    }

}
