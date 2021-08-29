package com.page.server.dao;

import com.page.server.entity.Ticket;

public interface TicketDao {
    Long getTicketNo();

    String getTicketName();

//    String getContent();

    Ticket.Status getStatus();

    Boolean getShared();
}
