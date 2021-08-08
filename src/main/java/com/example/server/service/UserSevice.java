package com.example.server.service;

import com.example.server.dto.Sign;
import com.example.server.dto.User;
import com.example.server.entity.UserEntity;
import com.example.server.repository.UserGroupRepository;
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
    private final UserGroupRepository userGroupRepository;

    private final UserConvert userConvert;

    private final PasswordEncoder passwordEncoder;

    public UserEntity createUser(Sign.InRequest request) throws IllegalArgumentException{
        if(userRepository.findByUserName(request.userName).isPresent()) {
            throw new IllegalArgumentException("You are already registered with this email!");
        }

        UserEntity user = userConvert.from(request);

        user.setPassword(passwordEncoder.encode(request.password));

        return userRepository.save(user);
    }

    public UserEntity getUserByUserName(String userName) throws IllegalArgumentException {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Account."));
    }

    public List<UserEntity> getAllUserList() {
        return userRepository.findAll();
    }

    public User.Info convertUser(UserEntity user) {
        User.Info userResponse = userConvert.to(user);

        if (user.getGroupNo() != null) {
            userGroupRepository.findById(user.getGroupNo())
                    .ifPresent(userGroup -> userResponse.groupName = userGroup.groupName);
        }

        return userResponse;
    }
}
