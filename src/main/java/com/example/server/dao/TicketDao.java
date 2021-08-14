package com.example.server.dao;

import com.example.server.entity.Ticket;

public interface TicketDao {
    public Long getTicketNo();

    public String getTitle();

    public String getContent();

    public Ticket.Status getStatus();
}
