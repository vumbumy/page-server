package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.dao.UserDao;
import com.page.server.dao.UserGroupDao;
import com.page.server.dto.PermissionDto;
import com.page.server.entity.Permission;
import com.page.server.entity.User;
import com.page.server.entity.base.BaseContent;
import com.page.server.repository.PermissionRepository;
import com.page.server.repository.UserGroupRepository;
import com.page.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    public List<PermissionDao.Content> getPermissionDaoListByUserNo(Long userNo) {
        List<Long> groupNoList = this.getUserGroupNoListByUserNo(userNo);

        return permissionRepository.findPermissionDaoListByUserNo(userNo, groupNoList);
    }

    public List<Long> getPublicContentNoList() {
        return permissionRepository.findPublicContentNoList();
    }

    public List<PermissionDao.No> getPermissionListByUserNo(Long userNo) {
        return permissionRepository.findPermissionListByUser(userNo);
    }

    public Permission getDefaultPermission(User user) {
        return this.createIfNotExist(
                Permission.builder()
                        .userNo(user.userNo)
                        .accessRight(AccessRight.WRITE)
                        .build()
        );
    }

    public Permission createIfNotExist(Permission permission) {
        return permissionRepository.findPermissionByUserNoAndGroupNoAndAccessRight(permission.userNo, permission.groupNo, permission.accessRight)
                .orElseGet(() -> permissionRepository.save(permission));
    }

    public List<Permission> addListIfNotExist(List<Permission> permissions) {
        if (CollectionUtils.isEmpty(permissions)) return new ArrayList<>();

        List<Permission> permissionList = new ArrayList<>();
        for(Permission permission : permissions) {
            permissionList.add(
                    this.createIfNotExist(permission)
            );
        }

        return permissionList;
    }

    public List<Long> getUserGroupNoListByUserNo(Long userNo) {
        List<UserGroupDao> daoList;

//        if (user.isAdmin()) {
//            daoList = userGroupRepository.findAllUserGroupNoList();
//        } else {
        List<PermissionDao.No> permissionList = this.getPermissionListByUserNo(userNo);

        daoList = userGroupRepository.findUserGroupNoListByPermissionNos(
                permissionList.stream().map(PermissionDao.No::getPermissionNo)
                        .collect(Collectors.toList())
        );
//        }

        return daoList.stream().map(UserGroupDao::getGroupNo)
                .collect(Collectors.toList());
    }

    public List<Long> getUserGroupNoListByUser(User user) {
        return this.getUserGroupNoListByUserNo(user.userNo);
    }

    public Boolean hasPermission(User user, BaseContent baseContent) {
        boolean readable;
        boolean writable;
        if (user.isAdmin()) {
            readable = true;
            writable = true;
        } else {
            List<Long> groupNoList = this.getUserGroupNoListByUser(user);

            readable = baseContent.isReadable(null, null) ||
                    baseContent.isReadable(user.userNo, groupNoList);

            writable = baseContent.isWritable(user.userNo, groupNoList);;
        }

        if (!readable && !writable) {
            throw new RuntimeException("You don't have permission.");
        }

        return writable;
    }

    public void checkPermission(User user, BaseContent baseContent) {
        boolean writable;
        if (user.isAdmin()) {
            writable = true;
        } else {
            List<Long> groupNoList = this.getUserGroupNoListByUser(user);

            writable = baseContent.isWritable(user.userNo, groupNoList);;
        }

        if (!writable) {
            throw new RuntimeException("You don't have permission.");
        }
    }

    public List<PermissionDto.User> getPermissionDtoListByPermissions(List<Permission> permissions) {
        List<UserDao> userDaoList = userRepository.findAllByUserNoIn(
                permissions.stream()
                        .map(permission -> permission.userNo)
                        .collect(Collectors.toList())
        );

        Map<Long, String> userMap = userDaoList.stream()
                .collect(Collectors.toMap(
                        UserDao::getUserNo,
                        UserDao::getEmail
                ));

        List<PermissionDto.User> userDtoList = new ArrayList<>();
        permissions.forEach(permission -> {
            if (permission.userNo == null) return;

            String userEmail = userMap.get(permission.userNo);

            userDtoList.add(
                    PermissionDto.User.builder()
                            .userNo(permission.userNo)
                            .email(userEmail)
                            .accessRight(permission.accessRight)
                            .build()
            );
        });

        return userDtoList;
    }
}
