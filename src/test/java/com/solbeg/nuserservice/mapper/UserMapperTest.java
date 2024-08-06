package com.solbeg.nuserservice.mapper;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.entity.UserArchive;
import com.solbeg.nuserservice.model.RegisterRequest;
import com.solbeg.nuserservice.model.UserArchiveResponse;
import com.solbeg.nuserservice.model.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;

import static com.solbeg.nuserservice.mapper.UserMapperBeansConfigTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@Import(UserMapperBeansConfigTest.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RegisterRequest registerRequest;
    @Autowired
    private User user;
    @Autowired
    private UserArchive userArchive;

    @Test
    public void testRegisterRequestToUser() {
        User expectedUser = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .isActive(isActive)
                .role(role)
                .build();
        assertEquals(expectedUser, userMapper.registerRequestToUser(registerRequest, role, isActive));
        expectedUser.setRole(wrongRole);
        assertNotEquals(expectedUser, userMapper.registerRequestToUser(registerRequest, role, isActive));
    }

    @Test
    public void testUserToUserResponse() {
        UserResponse expected = UserResponse.builder()
                .id(id)
                .username(email)
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .isActive(isActive)
                .isEmailVerified(isEmailVerified)
                .authorities(role.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();

        assertEquals(expected, userMapper.userToUserResponse(user));
        expected.setRole(wrongRole);
        assertNotEquals(expected, userMapper.userToUserResponse(user));
    }

    @Test
    public void testUserToUserArchiveResponse() {
        UserArchiveResponse expected = UserArchiveResponse.builder()
                .id(id)
                .role(role)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(isActive)
                .build();

        assertEquals(expected, userMapper.userToUserArchiveResponse(user));
        expected.setRole(wrongRole);
        assertNotEquals(expected, userMapper.userToUserArchiveResponse(user));
    }

    @Test
    public void testUserArchiveToUserArchiveResponse() {
        UserArchiveResponse expected = UserArchiveResponse.builder()
                .id(id)
                .role(role)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(isActive)
                .build();

        assertEquals(expected, userMapper.userArchiveToUserArchiveResponse(userArchive));
        expected.setRole(wrongRole);
        assertNotEquals(expected, userMapper.userArchiveToUserArchiveResponse(userArchive));
    }
}