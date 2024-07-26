package com.solbeg.nuserservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.solbeg.nuserservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findAllByActive(Boolean isActive, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update User u set u.isActive=true where u.id=:id")
    void activateUserById(@Param("id")Long id);

    @Query("select u from User u where u.id=:id and (u.isActive = false)")
    Optional<User> findNonActiveById(Long id);
}
