package com.example.server.controller;

import com.example.server.config.JwtTokenProvider;
import com.example.server.dto.PublicRequest;
import com.example.server.dto.UserResponse;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import com.example.server.service.UserSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class PublicController {
    private final UserSevice userSevice;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign/up")
    public ResponseEntity<UserResponse> createUser(@RequestBody PublicRequest.SignIn request) {
        try {
            User user = userSevice.createUser(request);
            return ResponseEntity.ok(
                    userSevice.convertUser(user)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/sign/in")
    public ResponseEntity<String> getToken(@RequestBody PublicRequest.SignIn request) {
        User member = userSevice.getUserByUserName(request.userName);
        if (!member.isEnabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!passwordEncoder.matches(request.password, member.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(
            jwtTokenProvider.createToken(member.getUsername(), Collections.emptyList())
        );
    }
}
