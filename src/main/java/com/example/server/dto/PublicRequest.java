package com.example.server.dto;

import lombok.AllArgsConstructor;

public class PublicRequest {
    @AllArgsConstructor
    public static class SignIn {
        public String userName;
        public String password;
    }
}
