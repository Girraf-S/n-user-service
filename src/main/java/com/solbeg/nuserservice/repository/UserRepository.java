package com.solbeg.nuserservice.repository;

import com.solbeg.nuserservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import com.solbeg.nuserservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByIsActiveIsFalseAndRole(Role role);

    @Transactional
    @Modifying
    @Query("update User u set u.isActive=true where u.id=:id")
    void activateUserById(@Param("id")Long id);
}
