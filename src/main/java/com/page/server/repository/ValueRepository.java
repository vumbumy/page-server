package com.page.server.repository;

import com.page.server.dao.CellDao;
import com.page.server.entity.data.DataCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValueRepository extends JpaRepository<DataCell, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    cell_no AS cellNo,\n" +
                    "    column_no AS columnNo,\n" +
                    "    cell_value AS cellValue\n" +
                    "FROM\n" +
                    "    _data_cell\n" +
                    "WHERE\n" +
                    "    content_no = ?1\n" +
                    "    AND column_no IN (?2)"
    )
    List<CellDao> findAllDaoByContentNo(Long contentNo, List<Long> typeNoList);

    List<DataCell> findAllByContentNo(Long contentNo);
}
