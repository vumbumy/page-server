package com.page.server.repository;

import com.page.server.dao.UserGroupDao;
import com.page.server.entity.Permission;
import com.page.server.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
//    @Query(
//            nativeQuery = true,
//            value = "SELECT group_name FROM _user_group WHERE group_no = ?1"
//    )
//    Optional<String> findGroupNameByGroupNo(Long groupNo);

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    ug.*\n" +
                    "FROM\n" +
                    "    _user_group AS ug\n" +
                    "    LEFT JOIN _user_group_permissions AS ugp ON ug.group_no = ugp.group_no\n" +
                    "WHERE\n" +
                    "    ugp.permission_no IN (?1)"
    )
    List<UserGroup> findUserGroupByPermissionNos(List<Long> permissionNos);

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    ug.group_no AS groupNo\n" +
                    "FROM\n" +
                    "    _user_group AS ug\n" +
                    "    LEFT JOIN _user_group_permissions AS ugp ON ug.group_no = ugp.group_no\n" +
                    "WHERE\n" +
                    "    ugp.permission_no IN (?1)"
    )
    List<UserGroupDao> findUserGroupNoListByPermissionNos(List<Long> permissionNos);

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    ug.group_no AS groupNo\n" +
                    "FROM\n" +
                    "    _user_group AS ug\n"
    )
    List<UserGroupDao> findAllUserGroupNoList();

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    ug.group_no AS groupNo\n" +
                    "FROM\n" +
                    "    _user_group AS ug\n" +
                    "    LEFT JOIN _user_group_permissions AS ugp ON ugp.group_no = ug.group_no\n" +
                    "    LEFT JOIN _permission AS pm ON ugp.permission_no = pm.permission_no\n" +
                    "WHERE\n" +
                    "    pm.user_no = ?1;"
    )
    List<UserGroupDao> findUserGroupNoListByUserNo(Long userNo);
}
