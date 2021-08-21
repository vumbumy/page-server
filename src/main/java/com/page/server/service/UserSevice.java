package com.page.server.service;

import com.page.server.dto.SignDto;
import com.page.server.dto.UserDto;
import com.page.server.entity.Role;
import com.page.server.entity.User;
import com.page.server.repository.RoleRepository;
import com.page.server.repository.UserGroupRepository;
import com.page.server.repository.UserRepository;
import com.page.server.support.UserConvert;
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
        User user = userConvert.from(request);

        user.setPassword(passwordEncoder.encode(request.password));
        user.setIsActivated(false);

        return userConvert.to(
                userRepository.save(user)
        );
    }

    public Boolean getUserIsActivated(String userName) {
        return userRepository.findUserNameIsActivated(userName);
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
                .orElseThrow(() -> new IllegalArgumentException("Can't find account."));
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
