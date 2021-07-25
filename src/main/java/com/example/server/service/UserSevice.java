package com.example.server.service;

import com.example.server.dto.Request;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import com.example.server.support.UserConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSevice {
    private final UserRepository userRepository;

    private final UserConvert userConvert;

    private final PasswordEncoder passwordEncoder;

    public User createUser(Request.SignIn request) throws IllegalArgumentException{
        if(userRepository.findByUserName(request.userName).isPresent()) {
            throw new IllegalArgumentException("ALREADY_EXIST");
        }

        User user = userConvert.from(request);

        user.setPassword(passwordEncoder.encode(request.password));

        return userRepository.save(user);
    }
}
