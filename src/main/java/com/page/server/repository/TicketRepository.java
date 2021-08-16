package com.page.server.repository;

import com.page.server.dao.TicketDao;
import com.page.server.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value =
            "SELECT \n" +
            "    tk.content_no as ticketNo,\n" +
            "    tk.title as title,\n" +
            "    tk.status as status\n" +
            "FROM\n" +
            "    _ticket AS tk\n" +
            "        LEFT JOIN\n" +
            "    _content_permissions AS cp ON cp.content_no = tk.content_no\n" +
            "WHERE\n" +
            "    ?2 IS NULL OR tk.status = ?2\n" +
            "    AND cp.permission_no IN (?1)",
            nativeQuery = true
    )
    List<TicketDao> findAllByPermissions(List<Long> permissionNoList, Integer status);

    @Query(value =
            "SELECT \n" +
                    "    tk.content_no as ticketNo,\n" +
                    "    tk.title as title,\n" +
                    "    tk.content as content,\n" +
                    "    tk.status as status\n" +
                    "FROM\n" +
                    "    _ticket AS tk\n" +
                    "WHERE\n" +
                    "    tk.is_public",
            nativeQuery = true
    )
    List<TicketDao> findPublicAll();

    @Query(value =
            "SELECT \n" +
                    "    tk.content_no as ticketNo,\n" +
                    "    tk.title as title,\n" +
                    "    tk.status as status\n" +
                    "FROM\n" +
                    "    _ticket AS tk\n" +
                    "WHERE\n" +
                    "    ?1 IS NULL OR tk.status = ?1\n",
            nativeQuery = true
    )
    List<TicketDao> findAllTicketDaoList(Integer status);
}
