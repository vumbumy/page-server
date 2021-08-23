package com.page.server.dto;

import com.page.server.entity.DataValue;
import com.page.server.entity.Permission;
import com.page.server.entity.Ticket;
import lombok.Builder;

import java.util.List;

@Builder
public class TicketDto {
    public Long ticketNo;
    public String title;
    public List<DataValue> values;
    public Boolean isPublic;
    public Ticket.Status status;
    public List<Permission> permissions;
    public Boolean isWriteable;
}
