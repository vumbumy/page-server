package com.page.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    public String email;
    public String phoneNumber;
    public List<String> roles;

    public static class Response extends UserDto{
        public String groupName;
        public LocalDateTime createdAt;

        @Builder
        public Response(String email, String phoneNumber, List<String> roles, String groupName, LocalDateTime createdAt) {
            super(email, phoneNumber, roles);
            this.groupName = groupName;
            this.createdAt = createdAt;
        }
    }

    public static class Request extends UserDto {
        public Long userNo;
        public String password;
    }
}
