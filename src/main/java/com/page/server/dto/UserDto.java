package com.page.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
public class UserDto {
    public String userName;
    public List<String> roles;

    public static class Info extends UserDto{
        public String userName;
        public String phoneNumber;
        public String groupName;

        @Builder
        public Info(String userName, List<String> roles, String userName1, String phoneNumber, String groupName) {
            super(userName, roles);
            this.userName = userName1;
            this.phoneNumber = phoneNumber;
            this.groupName = groupName;
        }
    }

    public static class UpdateRequest extends UserDto{
        public Long userNo;
        public String phoneNumber;
        public String password;
        public Long groupNo;

        public UpdateRequest(String userName, List<String> roles, Long userNo, String phoneNumber, String password, Long groupNo) {
            super(userName, roles);
            this.userNo = userNo;
            this.phoneNumber = phoneNumber;
            this.password = password;
            this.groupNo = groupNo;
        }
    }
}
