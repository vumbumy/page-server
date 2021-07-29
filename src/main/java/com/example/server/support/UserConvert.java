package com.example.server.support;

import com.example.server.dto.PublicRequest;
import com.example.server.dto.UserResponse;
import com.example.server.entity.Role;
import com.example.server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConvert {

    User from(PublicRequest.SignIn request);

    @Mapping(source = "username", target = "userName")
    UserResponse to(User user);

    default String roleToString(Role role) {
        return role.value;
    }
}
