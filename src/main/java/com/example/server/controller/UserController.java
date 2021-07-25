package com.example.server.controller;

import com.example.server.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    @GetMapping(value = "/secured/admin/users")
    ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping(value = "/secured/me")
    ResponseEntity<User> getMe() {
        return ResponseEntity.ok(null);
    }
}
