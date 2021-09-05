package com.page.server.repository;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByUserNo(Long userNo);

    @Query(value =
            "SELECT \n" +
            "    cp.content_no AS contentNo, pm.access_right AS accessRight\n" +
            "FROM\n" +
            "    _content_permissions AS cp\n" +
            "        LEFT JOIN\n" +
            "    _permission AS pm ON cp.permission_no = pm.permission_no\n" +
            "WHERE\n" +
            "    pm.user_no = ?1",
            nativeQuery = true
    )
    List<PermissionDao> findPermissionDaoListByUserNo(Long userNo);

    @Query(value =
            "SELECT \n" +
                    "    cp.content_no AS contentNo, pm.access_right AS accessRight\n" +
                    "FROM\n" +
                    "    _content_permissions AS cp\n" +
                    "        LEFT JOIN\n" +
                    "    _permission AS pm ON cp.permission_no = pm.permission_no\n" +
                    "WHERE\n" +
                    "    cp.content_no = ?2\n" +
                    "    AND pm.user_no = ?1",
            nativeQuery = true
    )
    Optional<PermissionDao> findPermissionDaoByUserNo(Long userNo, Long contentNo);

    Optional<Permission> findPermissionByUserNoAndAccessRight(Long userNo, AccessRight accessRight);
}
