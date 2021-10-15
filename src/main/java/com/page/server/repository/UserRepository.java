package com.page.server.repository;

import com.page.server.dao.UserDao;
import com.page.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String userName);

    @Query(
            nativeQuery = true,
            value = "SELECT \n" +
                    "            activated\n" +
                    "        FROM\n" +
                    "            _user\n" +
                    "        WHERE\n" +
                    "            email = ?1"
    )
    Boolean findEmailIsActivated(String userName);

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    user_no AS userNo,\n" +
                    "    email AS email\n" +
                    "FROM\n" +
                    "    _user\n"
    )
    List<UserDao> findAllUserDaoList();

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    user_no AS userNo,\n" +
                    "    email AS email\n" +
                    "FROM\n" +
                    "    _user\n" +
                    "WHERE\n" +
                    "    _user.user_no in (?1)\n"
    )
    List<UserDao> findAllByUserNoIn(List<Long> userNoList);
}
