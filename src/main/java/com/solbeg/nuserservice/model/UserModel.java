package com.solbeg.nuserservice.model;


import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Data
public class UserModel {
    private String username;
    private String password;
    private Set<SimpleGrantedAuthority> authorities;
    private boolean isActive;

    public UserModel(String username, String password, Set<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    public UserModel() {
    }
}
