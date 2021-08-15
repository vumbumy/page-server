package com.page.server.dto;

import lombok.AllArgsConstructor;

import java.util.List;

public class UserDto {

    @AllArgsConstructor
    public static class Info {
        public String userName;
        public String phoneNumber;
        public String groupName;
        public List<String> roles;
    }

    public static class UpdateRequest {
        public Long userNo;
        public String userName;
        public String phoneNumber;
        public String password;
        public List<String> roles;
        public Long groupNo;
    }
}
