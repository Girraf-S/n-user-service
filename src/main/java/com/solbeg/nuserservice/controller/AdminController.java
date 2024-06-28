package com.solbeg.nuserservice.controller;

import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @PostMapping("/activate/jour")
    @PreAuthorize("hasAuthority('activate:users')") //todo: or change to access by role
    public Page<User> showNonActiveJournalists(){
        return new PageImpl<>(userRepository.findAllByIsActiveIsFalseAndRole(Role.JOURNALIST));
    }

    @PreAuthorize("hasAuthority('activate:users')") //todo: or change to access by role
    @PutMapping("/activate/jour/{id}")
    public void activateJournalist(@PathVariable Long id){
        userRepository.activateUserById(id);
    }
}
