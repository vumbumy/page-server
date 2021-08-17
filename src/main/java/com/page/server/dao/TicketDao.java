package com.page.server.dao;

import com.page.server.entity.Ticket;

public interface TicketDao {
    Long getTicketNo();

    String getTitle();

    String getContent();

    Ticket.Status getStatus();

    Boolean getIsPublic();
}
