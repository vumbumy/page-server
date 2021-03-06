package com.page.server.service;

import com.page.server.dao.UserDao;
import com.page.server.dto.PermissionDto;
import com.page.server.dto.SignDto;
import com.page.server.dto.UserDto;
import com.page.server.entity.Permission;
import com.page.server.entity.Role;
import com.page.server.entity.User;
import com.page.server.repository.RoleRepository;
import com.page.server.repository.UserRepository;
import com.page.server.support.UserConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSevice {
    private final UserRepository userRepository;
//    private final UserGroupRepository userGroupRepository;

//    private final UserGroupService userGroupService;

    private final RoleRepository roleRepository;

    private final UserConvert userConvert;

    private final PasswordEncoder passwordEncoder;

    public User createUser(SignDto.InRequest request) throws IllegalArgumentException{
        User user = userConvert.from(request);

        user.password = passwordEncoder.encode(request.password);
        user.activated = false;

        return userRepository.save(user);
    }

    public Boolean getUserIsActivated(String userName) {
        return userRepository.findEmailIsActivated(userName);
    }

    public User updateUser(UserDto.Request updated) throws IllegalArgumentException{
        User oldUser = userRepository.findById(updated.userNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Account."));

        if (updated.password != null) {
            oldUser.password = passwordEncoder.encode(updated.password);
        }

        oldUser.email = updated.email;
        oldUser.phoneNumber = updated.phoneNumber;
//        oldUser.groupNo = updated.groupNo;

        List<Role> roleList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(updated.roles)) {
            for(String roleStr: updated.roles) {
                roleRepository.findByValue(roleStr).ifPresent(roleList::add);
            }
        }
        oldUser.roles = roleList;

        return userRepository.save(oldUser);
    }

    public User getUser(Long userNo) throws IllegalArgumentException{
        return userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Account."));
    }

    public User getUserByUserName(String userName) throws IllegalArgumentException {
        return userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("Can't find account."));
    }

    public List<UserDao> getAllUserDaoList() {
        return userRepository.findAllUserDaoList();
    }


    public List<User> getAllUserList() {
        return userRepository.findAll();
    }

    public UserDto.Response convertUser(User user) {
        return userConvert.toResponse(user);
    }
}
