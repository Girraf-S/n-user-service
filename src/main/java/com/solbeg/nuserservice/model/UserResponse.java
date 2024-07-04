package com.solbeg.nuserservice.model;


import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Data
public class UserResponse {
    private String username;
    private Set<SimpleGrantedAuthority> authorities;
    private boolean isActive;

    public UserResponse(String username, Set<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    public UserResponse() {
    }
}
