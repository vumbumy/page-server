package com.page.server.repository;

import com.page.server.dao.ValueDao;
import com.page.server.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {
    @Query(value = "SELECT value_no AS valueNo, type_no AS typeNo, data_value AS dataValue FROM _value WHERE content_no = ?1", nativeQuery = true)
    List<ValueDao> findAllByContentNo(Long contentNo);
}
