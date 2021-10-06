package com.page.server.repository;

import com.page.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userName);

    @Query(value =
            "SELECT \n" +
            "            activated\n" +
            "        FROM\n" +
            "            _user\n" +
            "        WHERE\n" +
            "            email = ?1",
            nativeQuery = true
    )
    Boolean findEmailIsActivated(String userName);

    @Query(
            nativeQuery = true,
            value = "SELECT u FROM _user as u LEFT JOIN _user_group_ref AS ugr ON u.user_no = ugr.user_no WHERE ugr.group_no IN (?1)"
    )
    List<User> findAllByGroupNoList(List<Long> groupNoList);

    List<User> findAllByUserNoIn(List<Long> userNoList);
}
