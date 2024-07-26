package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Optional<User> findNonActiveById(Long id){
        return userRepository.findNonActiveById(id);
    }

    @Transactional
    public Optional<User> findById(Long id){
        return userRepository.findById(id).filter(User::isActive);
    }

    @Transactional
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email).filter(User::isActive);
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public void activateUserById(Long id) {
        userRepository.activateUserById(id);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> findAllByActive(Boolean isActive, Pageable pageable) {
        return userRepository.findAllByActive(isActive, pageable);
    }
}
