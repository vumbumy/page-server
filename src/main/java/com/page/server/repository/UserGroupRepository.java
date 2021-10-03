package com.page.server.repository;

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
}
