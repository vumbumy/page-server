package com.example.server.controller;

import com.example.server.dto.User;
import com.example.server.entity.UserEntity;
import com.example.server.service.UserSevice;
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
    ResponseEntity<List<UserEntity>> getUserList() {
        return ResponseEntity.ok(
                userSevice.getAllUserList()
        );
    }

    @GetMapping(value = "/secured/me")
    ResponseEntity<User.Info> getMe(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(
                userSevice.convertUser(user)
        );
    }
}
