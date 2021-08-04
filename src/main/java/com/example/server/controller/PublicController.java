package com.example.server.controller;

import com.example.server.config.JwtTokenProvider;
import com.example.server.dto.Sign;
import com.example.server.dto.User;
import com.example.server.entity.UserEntity;
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

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign/up")
    public ResponseEntity<User.Info> createUser(@RequestBody Sign.InRequest request) {
        try {
            UserEntity user = userSevice.createUser(request);
            return ResponseEntity.ok(
                    userSevice.convertUser(user)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/sign/in")
    public ResponseEntity<Sign.InResponse> getToken(@RequestBody Sign.InRequest request) {
        UserEntity user = userSevice.getUserByUserName(request.userName);
        if (!user.isEnabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(
                Sign.InResponse.builder()
//                        .user(userSevice.convertUser(user))
                        .token(jwtTokenProvider.createToken(user.getUsername(), Collections.emptyList()))
                        .build()
        );
    }
}
