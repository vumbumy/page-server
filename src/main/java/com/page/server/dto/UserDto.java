package com.page.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class UserDto {
    public String email;
    public String phoneNumber;
    public List<String> roles;

    public static class Info extends UserDto{
        public String groupName;
        public LocalDateTime createdAt;

        @Builder
        public Info(String email, String phoneNumber, List<String> roles, String groupName, LocalDateTime createdAt) {
            super(email, phoneNumber, roles);
            this.groupName = groupName;
            this.createdAt = createdAt;
        }
    }

    public static class Request extends UserDto{
        public Long userNo;
        public String password;
        public Long groupNo;

        public Request(String email, String phoneNumber, List<String> roles, Long userNo, String password, Long groupNo) {
            super(email, phoneNumber, roles);
            this.userNo = userNo;
            this.password = password;
            this.groupNo = groupNo;
        }
    }
}
