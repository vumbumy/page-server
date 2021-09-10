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

    List<User> findAllByGroupNo(Long groupNo);
}
