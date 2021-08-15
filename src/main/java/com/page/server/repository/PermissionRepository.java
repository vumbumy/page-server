package com.page.server.repository;

import com.page.server.constant.AccessRight;
import com.page.server.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByUserNo(Long userNo);

    Optional<Permission> findPermissionByUserNoAndAccessRight(Long userNo, AccessRight accessRight);
}
