package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.entity.Permission;
import com.page.server.entity.User;
import com.page.server.entity.UserGroup;
import com.page.server.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;
    private final PermissionService permissionService;

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

    public UserGroup getUserGroup(User user, Long groupNo) {
        UserGroup userGroup = userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        if (!user.isAdmin() && !userGroup.isReadable(user.userNo, null)) {
            throw new RuntimeException("You don't have permission.");
        }

        return userGroup;
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

    public Boolean deleteUserGroup(User user, Long groupNo) {
        UserGroup oldUserGroup = userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        if (!user.isAdmin() && oldUserGroup.isWritable(user.userNo, null)) {
            throw new RuntimeException("You don't have permission.");
        }

        userGroupRepository.deleteById(groupNo);

        return userGroupRepository.findById(groupNo).isPresent();
    }

    public void addGroupListToReadIfNotExist(Long userNo, List<Long> groupNos) {
        List<UserGroup> userGroupList = new ArrayList<>();

        Permission permission = permissionService.createIfNotExist(
                Permission.builder()
                        .userNo(userNo)
                        .accessRight(AccessRight.READ)
                        .build()
        );

        groupNos.forEach(groupNo -> {
            Optional<UserGroup> optional = userGroupRepository.findById(groupNo);
            if(!optional.isPresent()) {
                log.error("addIfNotExist {} {}", userNo, groupNo);
            } else {
                UserGroup userGroup = optional.get();

                userGroup.permissions.add(permission);

                userGroupList.add(userGroup);
            }
        });

        userGroupRepository.saveAll(userGroupList);
    }
}
