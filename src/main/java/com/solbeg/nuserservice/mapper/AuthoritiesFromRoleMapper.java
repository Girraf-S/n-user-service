package com.solbeg.nuserservice.mapper;

import com.solbeg.nuserservice.annotations.CustomRule;
import com.solbeg.nuserservice.entity.Role;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthoritiesFromRoleMapper {
    @CustomRule
    public Set<SimpleGrantedAuthority> getAuthorities(Role role){
        return role.getAuthorities();
    }
}
