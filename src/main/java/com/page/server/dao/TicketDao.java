package com.page.server.dao;

import com.page.server.entity.Ticket;

public interface TicketDao {
    public Long getTicketNo();

    public String getTitle();

    public String getContent();

    public Ticket.Status getStatus();

    public Boolean getIsPublic();
}
