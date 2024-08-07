package com.solbeg.nuserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.solbeg.nuserservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "update users set is_active=true where id=:id", nativeQuery = true)
    void activateUserById(Long id);
}
