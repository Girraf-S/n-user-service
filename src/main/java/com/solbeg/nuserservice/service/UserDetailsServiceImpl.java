package com.solbeg.nuserservice.service;

import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.UserResponse;
import com.solbeg.nuserservice.security.UserDetailsImpl;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " There is not such in REPO"));
    }

    public UserResponse getUser(String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " There is not such in REPO"));
        return userMapper.userToUserResponse(user);//new UserResponse(user.getEmail(), "[SUCURITY]", user.getRole().getAuthorities(), user.isActive());
    }
}
