package com.example.server.controller;

import com.example.server.config.JwtTokenProvider;
import com.example.server.dto.Request;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import com.example.server.service.UserSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<User> createUser(@RequestBody Request.SignIn request) {
        try {
            return ResponseEntity.ok(
                    userSevice.createUser(request)
            );
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/sign/in")
    public ResponseEntity<String> getToken(@RequestBody Request.SignIn request) {
        User member = userRepository.findByUserName(request.userName)
            .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        if (!passwordEncoder.matches(request.password, member.getPassword())) {
            throw new IllegalArgumentException("Wrong Password");
        }

        return ResponseEntity.ok(
            jwtTokenProvider.createToken(member.getUsername(), Collections.emptyList())
        );
    }
}
