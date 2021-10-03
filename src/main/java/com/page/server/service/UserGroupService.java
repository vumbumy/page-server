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

    public List<UserGroup> getAllUserGroupList() {
        return userGroupRepository.findAll();
    }

    public List<UserGroup> getUserGroupListByUser(User user) {
        if (user.isAdmin()) {
            return getAllUserGroupList();
        }

        List<PermissionDao.No> permissionList = permissionService.getPermissionListByUserNo(user.userNo);
        return userGroupRepository.findUserGroupByPermissionNos(
                permissionList.stream().map(PermissionDao.No::getPermissionNo)
                        .collect(Collectors.toList())
        );
    }

    public UserGroup getUserGroup(Long groupNo) {
        return userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));
    }

    public UserGroup updateUserGroup(UserGroup userGroup) {
        userGroupRepository.findById(userGroup.groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        return userGroupRepository.save(userGroup);
    }

    public Boolean deleteUserGroup(Long groupNo) {
        userGroupRepository.findById(groupNo)
                .orElseThrow(() -> new IllegalArgumentException("Can't Find Group."));

        userGroupRepository.deleteById(groupNo);

        return userGroupRepository.findById(groupNo).isPresent();
    }

    public void addGroupPermissionIfNotExist(Long userNo, List<Long> groupNos) {
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
