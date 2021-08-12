package com.example.server.repository;

import com.example.server.dao.TicketDao;
import com.example.server.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value =
            "SELECT \n" +
            "    tk.content_no as ticketNo,\n" +
            "    tk.content as content,\n" +
            "    tk.status as status\n" +
            "FROM\n" +
            "    _ticket AS tk\n" +
            "        LEFT JOIN\n" +
            "    _ticket_permissions AS tp ON tp.content_no = tk.content_no\n" +
            "WHERE\n" +
            "    tp.permission_no IN (?1)",
            nativeQuery = true
    )
    List<TicketDao> findAllByPermissions(List<Long> permissionNoList);

    @Query(value =
            "SELECT \n" +
                    "    tk.content_no as ticketNo,\n" +
                    "    tk.content as content,\n" +
                    "    tk.status as status\n" +
                    "FROM\n" +
                    "    _ticket AS tk\n",
            nativeQuery = true
    )
    List<TicketDao> findAllTicketDaoList();
}
