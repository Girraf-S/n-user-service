package com.solbeg.nuserservice.model;

import com.solbeg.nuserservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private String username;
    private String firstName;
    private String lastName;
    private Role role;
    private Set<String> authorities;
    private boolean isActive;
    private boolean isEmailVerified;
}
