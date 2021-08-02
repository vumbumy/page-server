package com.example.server.controller;

import com.example.server.dto.UserResponse;
import com.example.server.entity.User;
import com.example.server.service.UserSevice;
import com.example.server.support.UserConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserSevice userSevice;

    @GetMapping(value = "/secured/admin/users")
    ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(
                userSevice.getAllUserList()
        );
    }

    @GetMapping(value = "/secured/me")
    ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                userSevice.convertUser(user)
        );
    }
}
