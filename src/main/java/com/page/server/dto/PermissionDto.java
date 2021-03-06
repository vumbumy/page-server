package com.page.server.dto;

import com.page.server.constant.AccessRight;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
public class PermissionDto {
    public AccessRight accessRight;

    public static class User extends PermissionDto {
        public Long userNo;
        public String email;

        @Builder
        public User(AccessRight accessRight, Long userNo, String email) {
            super(accessRight);
            this.userNo = userNo;
            this.email = email;
        }
    }

    public static class Group extends PermissionDto {
        public Long groupNo;
        public String groupName;

        @Builder
        public Group(AccessRight accessRight, Long groupNo, String groupName) {
            super(accessRight);
            this.groupNo = groupNo;
            this.groupName = groupName;
        }
    }
}
