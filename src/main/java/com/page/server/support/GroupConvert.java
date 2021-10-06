package com.page.server.support;

import com.page.server.dto.GroupDto;
import com.page.server.dto.PermissionDto;
import com.page.server.entity.UserGroup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupConvert {

    GroupDto.Response toResponse(UserGroup userGroup, List<PermissionDto.User> userList);
}
