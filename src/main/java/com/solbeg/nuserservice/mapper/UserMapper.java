package com.solbeg.nuserservice.mapper;

import com.solbeg.nuserservice.annotations.CustomRule;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.RegisterRequest;
import com.solbeg.nuserservice.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {PasswordEncoderMapper.class, AuthoritiesFromRoleMapper.class})
public interface UserMapper {

    @Mapping(target = "firstName", source = "registerRequest.firstName")
    @Mapping(target = "lastName", source = "registerRequest.lastName")
    @Mapping(target = "email", source = "registerRequest.email")
    @Mapping(target = "password", source = "registerRequest.password", qualifiedBy = CustomRule.class)
    User registerRequestToUser(RegisterRequest registerRequest);

    @Mapping(target = "username", source = "user.email")
    @Mapping(target = "authorities", source = "user.role", qualifiedBy = CustomRule.class)
    @Mapping(target = "active", source = "user.active")
    UserResponse userToUserResponse(User user);
}
