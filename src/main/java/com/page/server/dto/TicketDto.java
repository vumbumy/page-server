package com.page.server.dto;

import com.page.server.entity.DataValue;
import com.page.server.entity.Permission;
import com.page.server.entity.Ticket;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public class TicketDto {
    public Long ticketNo;
    public String ticketName;
    public Boolean shared;
    public Ticket.Status status;
    public List<Permission> permissions;
    public Boolean isWriteable;

    public TicketDto(Long ticketNo, String ticketName, Boolean shared, Ticket.Status status, List<Permission> permissions, Boolean isWriteable) {
        this.ticketNo = ticketNo;
        this.ticketName = ticketName;
        this.shared = shared;
        this.status = status;
        this.permissions = permissions;
        this.isWriteable = isWriteable;
    }

    public static class Response extends TicketDto{
        public List<DataValue> values;

        @Builder
        public Response(Long ticketNo, String ticketName, Boolean shared, Ticket.Status status, List<Permission> permissions, Boolean isWriteable, List<DataValue> values) {
            super(ticketNo, ticketName, shared, status, permissions, isWriteable);
            this.values = values;
        }
    }

    public static class Request extends TicketDto {
        public Map<Long, String> values;

        @Builder
        public Request(Long ticketNo, String ticketName, Boolean shared, Ticket.Status status, List<Permission> permissions, Boolean isWriteable, Map<Long, String> values) {
            super(ticketNo, ticketName, shared, status, permissions, isWriteable);
            this.values = values;
        }
    }
}
