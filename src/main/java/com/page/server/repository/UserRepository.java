package com.page.server.repository;

import com.page.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    @Query(value =
            "SELECT \n" +
            "            is_activated\n" +
            "        FROM\n" +
            "            _user\n" +
            "        WHERE\n" +
            "            user_name = ?1",
            nativeQuery = true
    )
    Boolean findUserNameIsActivated(String userName);
}
