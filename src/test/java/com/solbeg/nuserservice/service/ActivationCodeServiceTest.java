package com.solbeg.nuserservice.service;


import com.solbeg.nuserservice.entity.ActivationCode;
import com.solbeg.nuserservice.repository.ActivationCodeRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActivationCodeServiceTest {

    @Autowired
    private ActivationCodeRepository activationCodeRepository;

    @Test
    @Transactional
    public void deleteExpiredCode(){
        String uuid = String.valueOf(UUID.randomUUID());
        ActivationCode activationCode = ActivationCode.builder()
                .userId(1L)
                .expiredAt(LocalDateTime.now().plusMinutes(5L))
                .code(uuid)
                .build();
        activationCodeRepository.save(activationCode);

        assertTrue(activationCodeRepository.findByCode(uuid).isPresent());
        activationCodeRepository.deleteActivationCodeByExpiredAtBefore(LocalDateTime.now().plusMinutes(10));

        assertTrue(activationCodeRepository.findByCode(uuid).isEmpty());
    }
}
