package com.example.server.support;

import com.example.server.dto.Request;
import com.example.server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConvert {

    @Mapping(target = "userNo", ignore = true)
    User from(Request.SignIn request);
}
