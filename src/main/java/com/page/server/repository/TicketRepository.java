package com.page.server.repository;

import com.page.server.dao.TicketDao;
import com.page.server.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value =
            "SELECT \n" +
            "    tk.content_no AS ticketNo,\n" +
            "    tk.content_name AS ticketName,\n" +
            "    tk.status AS status\n" +
            "FROM\n" +
            "    _ticket AS tk\n" +
            "WHERE\n" +
            "    (?1 IS NULL OR tk.project_no = ?1)\n" +
            "    AND (?3 IS NULL OR tk.status = ?3)\n" +
            "    AND tk.content_no IN (?2)",
            nativeQuery = true
    )
    List<TicketDao> findAllByTicketNoContains(Long projectNo, Set<Long> ticketNoList, Integer status);

    @Query(value =
            "SELECT \n" +
            "    tk.content_no as ticketNo,\n" +
            "    tk.content_name as ticketName,\n" +
//            "    tk.content as content,\n" +
            "    tk.status as status\n" +
            "FROM\n" +
            "    _ticket AS tk\n" +
            "WHERE\n" +
            "    (?1 IS NULL OR tk.project_no = ?1)\n" +
            "    AND tk.content_no IN (?2)",
            nativeQuery = true
    )
    List<TicketDao> findAllReadable(Long projectNo, List<Long> ticketNoList);

    @Query(value =
            "SELECT \n" +
            "    tk.content_no as ticketNo,\n" +
            "    tk.content_name as ticketName,\n" +
            "    tk.status as status\n" +
            "FROM\n" +
            "    _ticket AS tk\n" +
            "WHERE\n" +
            "    (?1 IS NULL OR tk.project_no = ?1)\n" +
            "    AND (?2 IS NULL OR tk.status = ?2)\n",
            nativeQuery = true
    )
    List<TicketDao> findAllTicketDaoList(Long projectNo, Integer status);
}
