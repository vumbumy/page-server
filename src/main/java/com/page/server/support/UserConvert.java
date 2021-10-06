package com.page.server.support;

import com.page.server.dto.SignDto;
import com.page.server.dto.UserDto;
import com.page.server.entity.Role;
import com.page.server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConvert {

    User from(SignDto.InRequest request);

    UserDto.Response toResponse(User user);

    default String roleToString(Role role) {
        return role.value;
    }
}
