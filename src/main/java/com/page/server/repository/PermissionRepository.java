package com.page.server.repository;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
//    List<Permission> findAllByUserNo(Long userNo);

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    cp.content_no AS contentNo,\n" +
                    "    pm.access_right AS accessRight\n" +
                    "FROM\n" +
                    "    _content_permissions AS cp\n" +
                    "    LEFT JOIN _permission AS pm ON cp.permission_no = pm.permission_no\n" +
                    "WHERE\n" +
                    "    (\n" +
                    "        pm.user_no IS NULL\n" +
                    "        AND pm.group_no IS NULL\n" +
                    "    )\n" +
                    "    OR (\n" +
                    "        ?1 IS NULL\n" +
                    "        OR pm.user_no = ?1\n" +
                    "    )\n" +
                    "    OR (\n" +
                    "        (?2) IS NULL\n" +
                    "        OR pm.group_no IN (?2)\n" +
                    "    )"
    )
    List<PermissionDao.Content> findPermissionDaoListByUserNo(Long userNo, List<Long> groupNoList);

    @Query(
            nativeQuery = true,
            value = "SELECT \n" +
                    "    permission_no AS permissionNo,\n" +
                    "    access_right AS accessRight\n" +
                    "FROM\n" +
                    "    _permission\n" +
                    "WHERE\n" +
                    "    user_no = ?1"
    )
    List<PermissionDao.No> findPermissionListByUser(Long userNo);

    @Query(
            nativeQuery = true,
            value = "SELECT \n" +
                    "    cp.content_no\n" +
                    "FROM\n" +
                    "    _content_permissions AS cp\n" +
                    "        LEFT JOIN\n" +
                    "    _permission AS pm ON cp.permission_no = pm.permission_no\n" +
                    "WHERE\n" +
                    "    pm.user_no IS NULL AND pm.group_no IS NULL\n"
    )
    List<Long> findPublicContentNoList();

    Optional<Permission> findPermissionByUserNoAndGroupNoAndAccessRight(Long userNo, Long groupNo, AccessRight accessRight);
}
