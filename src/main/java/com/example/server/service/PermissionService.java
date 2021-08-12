package com.example.server.service;

import com.example.server.entity.Permission;
import com.example.server.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public List<Long> getPermissionNoListByUserNo(Long userNo) {
        List<Permission> permissionList = permissionRepository.findAllByUserNo(userNo);

        return permissionList.stream()
                .map(permission -> permission.permissionNo)
                .collect(Collectors.toList());
    }

    public Permission createIfNotExist(Permission permission) {
        return permissionRepository.findPermissionByUserNoAndAccessRight(permission.userNo, permission.accessRight)
                .orElseGet(() -> permissionRepository.save(permission));
    }

    public List<Permission> addListIfNotExist(List<Permission> permissions) {
        if (CollectionUtils.isEmpty(permissions)) return null;

        List<Permission> permissionList = new ArrayList<>();
        for(Permission permission : permissions) {
            permissionList.add(
                    this.createIfNotExist(permission)
            );
        }

        return permissionList;
    }
}
