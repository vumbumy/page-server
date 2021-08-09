package com.example.server.service;

import com.example.server.dto.SignDto;
import com.example.server.dto.UserDto;
import com.example.server.entity.Role;
import com.example.server.entity.User;
import com.example.server.repository.RoleRepository;
import com.example.server.repository.UserGroupRepository;
import com.example.server.repository.UserRepository;
import com.example.server.support.UserConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSevice {
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final RoleRepository roleRepository;

    private final UserConvert userConvert;

    private final PasswordEncoder passwordEncoder;

    public UserDto.Info createUser(SignDto.InRequest request) throws IllegalArgumentException{
        if(userRepository.findByUserName(request.userName).isPresent()) {
            throw new IllegalArgumentException("You are already registered with this email!");
        }

        User user = userConvert.from(request);

        user.setPassword(passwordEncoder.encode(request.password));

        return userConvert.to(
                userRepository.save(user)
        );
    }

    public UserDto.Info updateUser(UserDto.UpdateRequest updated) throws IllegalArgumentException{
        User oldUser = userRepository.findById(updated.userNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Account."));

        if (updated.userName != null) {
            oldUser.setUserName(updated.userName);
        }

        if (updated.password != null) {
            oldUser.setPassword(passwordEncoder.encode(updated.password));
        }

        oldUser.setPhoneNumber(updated.phoneNumber);
        oldUser.setGroupNo(updated.groupNo);

        List<Role> roleList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(updated.roles)) {
            for(String roleStr: updated.roles) {
                roleRepository.findByValue(roleStr).ifPresent(roleList::add);
            }
        }
        oldUser.setRoles(roleList);

        return userConvert.to(
                userRepository.save(oldUser)
        );
    }

    public User getUserByUserName(String userName) throws IllegalArgumentException {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Account."));
    }

    public List<User> getAllUserList() {
        return userRepository.findAll();
    }

    public UserDto.Info convertUser(User user) {
        UserDto.Info userResponse = userConvert.to(user);

        if (user.getGroupNo() != null) {
            userGroupRepository.findById(user.getGroupNo())
                    .ifPresent(userGroup -> userResponse.groupName = userGroup.groupName);
        }

        return userResponse;
    }
}
