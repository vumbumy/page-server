package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
public class Sign {
    public static class InRequest {
        public String userName;
        public String password;
    }

    @Builder
    public static class InResponse {
//        public User.Info user;
        public String token;
    }
}
