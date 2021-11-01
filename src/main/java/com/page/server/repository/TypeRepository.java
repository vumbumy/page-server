package com.page.server.repository;

import com.page.server.entity.data.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {
    List<Type> findAllByDeletedFalseOrDeletedIsNull();

    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    tp.type_no\n" +
                    "FROM\n" +
                    "    _type AS tp\n" +
                    "    LEFT JOIN _project_types as pt ON tp.type_no = pt.type_no\n" +
                    "WHERE\n" +
                    "    pt.project_no = ?1\n" +
                    "    AND tp.deleted IS NOT TRUE"
    )
    List<Long> findAllByProjectNoAndDeletedFalse(Long projectNo);

    Optional<Type> findTypeByTypeNameAndDataTypeAndRequiredAndDefaultValueAndDeleted(String type, Type.DataType dataType, Boolean required, String defaultValue, Boolean deleted);
}
