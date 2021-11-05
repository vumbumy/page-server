package com.page.server.repository;

import com.page.server.entity.data.DataColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<DataColumn, Long> {
    List<DataColumn> findAllByDeletedFalseOrDeletedIsNull();

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    tp.column_no\n" +
                    "FROM\n" +
                    "    _type AS tp\n" +
                    "    LEFT JOIN _project_types as pt ON tp.column_no = pt.column_no\n" +
                    "WHERE\n" +
                    "    pt.project_no = ?1\n" +
                    "    AND tp.deleted IS NOT TRUE"
    )
    List<Long> findAllByProjectNoAndDeletedFalse(Long projectNo);

    Optional<DataColumn> findTypeByColumnNameAndColumnTypeAndRequiredAndDefaultValueAndDeleted(String type, DataColumn.Type columnType, Boolean required, String defaultValue, Boolean deleted);
}
