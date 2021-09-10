package com.page.server.repository;

import com.page.server.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT group_name FROM _user_group WHERE group_no = ?1"
    )
    Optional<String> findGroupNameByGroupNo(Long groupNo);
}
