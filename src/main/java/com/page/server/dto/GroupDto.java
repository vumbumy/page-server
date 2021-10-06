package com.page.server.dto;

import com.page.server.entity.Permission;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class GroupDto {
    public String groupName;

    public static class Request extends GroupDto{
        public List<Permission> permissions;
    }

    public static class Response extends GroupDto{
        public List<PermissionDto.User> userList;
    }
}
