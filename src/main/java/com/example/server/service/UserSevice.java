package com.example.server.service;

import com.example.server.dto.PublicRequest;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import com.example.server.support.UserConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSevice {
    private final UserRepository userRepository;

    private final UserConvert userConvert;

    private final PasswordEncoder passwordEncoder;

    public User createUser(PublicRequest.SignIn request) throws IllegalArgumentException{
        if(userRepository.findByUserName(request.userName).isPresent()) {
            throw new IllegalArgumentException("ALREADY_EXIST");
        }

        User user = userConvert.from(request);

        user.setPassword(passwordEncoder.encode(request.password));

        return userRepository.save(user);
    }

    public User getUserByUserName(String userName) throws IllegalArgumentException {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
    }

    public List<User> getAllUserList() {
        return userRepository.findAll();
    }
}
