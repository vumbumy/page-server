package com.page.server.controller;

import com.page.server.dto.UserDto;
import com.page.server.entity.User;
import com.page.server.service.UserSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured/admin/users")
public class UserController {
    private final UserSevice userSevice;

    @GetMapping(value = "")
    ResponseEntity<List<User>> getUserList(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                userSevice.getAllUserList(user)
        );
    }

    @GetMapping(value = "/{userNo}")
    ResponseEntity<User> getUser(@PathVariable Long userNo) {
        return ResponseEntity.ok(
                userSevice.getUser(userNo)
        );
    }

    @PutMapping(value = "")
    ResponseEntity<User> updateUser(@RequestBody UserDto.Request userEntity) {
        return ResponseEntity.ok(
                userSevice.updateUser(userEntity)
        );
    }
}
