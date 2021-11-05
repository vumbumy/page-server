package com.page.server.repository;

import com.page.server.dao.ValueDao;
import com.page.server.entity.data.DataValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValueRepository extends JpaRepository<DataValue, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    value_no AS valueNo,\n" +
                    "    column_no AS typeNo,\n" +
                    "    data_value AS dataValue\n" +
                    "FROM\n" +
                    "    _value\n" +
                    "WHERE\n" +
                    "    content_no = ?1\n" +
                    "    AND column_no IN (?2)"
    )
    List<ValueDao> findAllDaoByContentNo(Long contentNo, List<Long> typeNoList);

    List<DataValue> findAllByContentNo(Long contentNo);
}
