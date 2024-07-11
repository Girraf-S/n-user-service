package com.solbeg.nuserservice.mapper;

import com.solbeg.nuserservice.annotations.CustomRule;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.model.RegisterRequest;
import com.solbeg.nuserservice.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {PasswordEncoderMapper.class})
public interface UserMapper {

    @Mapping(target = "password", source = "registerRequest.password", qualifiedBy = CustomRule.class)
    User registerRequestToUser(RegisterRequest registerRequest);

    @Mapping(target = "username", source = "user.email")
    UserResponse userToUserResponse(User user);
}
