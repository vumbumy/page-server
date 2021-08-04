package com.example.server.dto;

import lombok.AllArgsConstructor;

import java.util.List;

public class User {

    @AllArgsConstructor
    public static class Info {
        public String userName;
        public String phoneNumber;
        public String groupName;
        public List<String> roles;
    }
}
