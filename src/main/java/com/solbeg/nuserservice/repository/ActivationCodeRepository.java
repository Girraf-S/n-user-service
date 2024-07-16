package com.solbeg.nuserservice.repository;

import com.solbeg.nuserservice.entity.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationCodeRepository extends JpaRepository<ActivationCode, Long> {
    ActivationCode findByCode(String code);
}
