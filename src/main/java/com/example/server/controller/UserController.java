package com.example.server.controller;

import com.example.server.dto.UserDto;
import com.example.server.entity.User;
import com.example.server.service.UserSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured/admin")
public class UserController {
    private final UserSevice userSevice;

    @GetMapping(value = "/users")
    ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(
                userSevice.getAllUserList()
        );
    }

    @PutMapping(value = "/users")
    ResponseEntity<UserDto.Info> updateUser(@RequestBody UserDto.UpdateRequest userEntity) {
        return ResponseEntity.ok(
                userSevice.updateUser(userEntity)
        );
    }
}
