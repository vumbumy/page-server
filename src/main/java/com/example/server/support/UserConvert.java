package com.example.server.support;

import com.example.server.dto.Sign;
import com.example.server.dto.User;
import com.example.server.entity.Role;
import com.example.server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConvert {

    UserEntity from(Sign.InRequest request);

    @Mapping(source = "username", target = "userName")
    User.Info to(UserEntity user);

    default String roleToString(Role role) {
        return role.value;
    }
}
