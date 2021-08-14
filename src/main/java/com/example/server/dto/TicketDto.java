package com.example.server.dto;

import com.example.server.entity.Permission;
import com.example.server.entity.Ticket;
import lombok.Builder;

import java.util.List;

@Builder
public class TicketDto {
    public Long ticketNo;
    public String title;
    public String content;
    public Ticket.Status status;
    public List<Permission> permissions;
}
