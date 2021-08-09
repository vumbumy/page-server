package com.example.server.support;

import com.example.server.dto.SignDto;
import com.example.server.dto.UserDto;
import com.example.server.entity.Role;
import com.example.server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConvert {

    User from(SignDto.InRequest request);

    @Mapping(source = "username", target = "userName")
    UserDto.Info to(User user);

    default String roleToString(Role role) {
        return role.value;
    }
}
