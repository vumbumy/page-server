package com.page.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
public class SignDto {
    public static class InRequest {
        public String email;
        public String password;
    }

//    @Builder
//    public static class InResponse {
//        public String token;
//    }
}
