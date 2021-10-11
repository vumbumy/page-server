package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.dto.GroupDto;
import com.page.server.dto.PermissionDto;
import com.page.server.entity.Permission;
import com.page.server.entity.User;
import com.page.server.entity.UserGroup;
import com.page.server.repository.UserGroupRepository;
import com.page.server.repository.UserRepository;
import com.page.server.support.UserGroupConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;
    private final PermissionService permissionService;

    private final UserRepository userRepository;

    private final UserGroupConvert userGroupConvert;

//    public List<UserGroup> getAllUserGroupList() {
//        return userGroupRepository.findAll();
//    }

    public List<UserGroup> getUserGroupListByUser(User user) {
        if (user.isAdmin()) {
            return userGroupRepository.findAll();
        }

        List<PermissionDao.No> permissionList = permissionService.getPermissionListByUserNo(user.userNo);
        return userGroupRepository.findUserGroupByPermissionNos(
                permissionList.stream().map(PermissionDao.No::getPermissionNo)
                        .collect(Collectors.toList())
        );
    }

    public UserGroup addUserGroup(User user, UserGroup userGroup) {
        Permission writePermission = permissionService.createIfNotExist(
                Permission.builder()
                        .userNo(user.userNo)
                        .accessRight(AccessRight.WRITE)
                        .build()
        );

        if (!user.isAdmin() && CollectionUtils.isEmpty(userGroup.permissions)) {
            userGroup.permissions = new ArrayList<>();
        }

        userGroup.permissions.add(writePermission);

        return userGroupRepository.save(userGroup);
    }

    public GroupDto.Response getUserGroup(User user, Long groupNo) {
        UserGroup userGroup = userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        if (!user.isAdmin() && !userGroup.isReadable(user.userNo, null)) {
            throw new RuntimeException("You don't have permission.");
        }

        List<User> userList = userRepository.findAllByUserNoIn(
                userGroup.permissions.stream()
                        .map(permission -> permission.userNo)
                        .collect(Collectors.toList())
        );

        Map<Long, User> userMap = userList.stream()
                .collect(Collectors.toMap(u -> u.userNo, u -> u));

        List<PermissionDto.User> userDtoList = new ArrayList<>();
        userGroup.permissions.forEach(permission -> {
            if (permission.userNo == null) return;

            User u = userMap.get(permission.userNo);

            userDtoList.add(
                    PermissionDto.User.builder()
                            .userNo(u.userNo)
                            .email(u.email)
                            .accessRight(permission.accessRight)
                            .build()
            );
        });

        return userGroupConvert.toResponse(userGroup, userDtoList);
    }

    @Transactional
    public UserGroup updateUserGroup(User user, UserGroup userGroup) {
        UserGroup oldUserGroup = userGroupRepository.findById(userGroup.groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        if (!user.isAdmin() && !oldUserGroup.isWritable(user.userNo, null)) {
            throw new RuntimeException("You don't have permission.");
        }

        return userGroupRepository.save(userGroup);
    }

    @Transactional
    public UserGroup updateUserGroupPermissions(User user, Long groupNo, List<PermissionDto.User> userPermissions) {
        UserGroup userGroup = userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        if (!user.isAdmin() && !userGroup.isWritable(user.userNo, null)) {
            throw new RuntimeException("You don't have permission.");
        }

        userGroup.permissions = this.addUserPermissionList(userPermissions);

        return userGroupRepository.save(userGroup);
    }

    public Boolean deleteUserGroup(User user, Long groupNo) {
        UserGroup oldUserGroup = userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        if (!user.isAdmin() && oldUserGroup.isWritable(user.userNo, null)) {
            throw new RuntimeException("You don't have permission.");
        }

        userGroupRepository.deleteById(groupNo);

        return userGroupRepository.findById(groupNo).isPresent();
    }

//    public void addGroupListToReadIfNotExist(Long userNo, List<Long> groupNos) {
//        List<UserGroup> userGroupList = new ArrayList<>();
//
//        Permission permission = permissionService.createIfNotExist(
//                Permission.builder()
//                        .userNo(userNo)
//                        .accessRight(AccessRight.READ)
//                        .build()
//        );
//
//        groupNos.forEach(groupNo -> {
//            Optional<UserGroup> optional = userGroupRepository.findById(groupNo);
//            if(!optional.isPresent()) {
//                log.error("addIfNotExist {} {}", userNo, groupNo);
//            } else {
//                UserGroup userGroup = optional.get();
//
//                userGroup.permissions.add(permission);
//
//                userGroupList.add(userGroup);
//            }
//        });
//
//        userGroupRepository.saveAll(userGroupList);
//    }

    public List<Permission> addUserPermissionList(List<PermissionDto.User> userPermissions) {
        List<Permission> permissionList = new ArrayList<>();
        userPermissions.forEach(user -> {
            permissionList.add(
                    Permission.builder()
                            .userNo(user.userNo)
                            .accessRight(user.accessRight)
                            .build()
            );
        });

        return permissionService.addListIfNotExist(permissionList);
    }
}
