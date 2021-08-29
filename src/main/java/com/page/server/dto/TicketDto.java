package com.page.server.dto;

import com.page.server.entity.Value;
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
    public Boolean writeable;

    public TicketDto(Long ticketNo, String ticketName, Boolean shared, Ticket.Status status, List<Permission> permissions, Boolean writeable) {
        this.ticketNo = ticketNo;
        this.ticketName = ticketName;
        this.shared = shared;
        this.status = status;
        this.permissions = permissions;
        this.writeable = writeable;
    }

    public static class Response extends TicketDto{
        public List<Value> values;

        @Builder
        public Response(Long ticketNo, String ticketName, Boolean shared, Ticket.Status status, List<Permission> permissions, Boolean writeable, List<Value> values) {
            super(ticketNo, ticketName, shared, status, permissions, writeable);
            this.values = values;
        }
    }

    public static class Request extends TicketDto {
        public Long projectNo;

        public Map<Long, String> values;

        @Builder
        public Request(Long ticketNo, String ticketName, Boolean shared, Ticket.Status status, List<Permission> permissions, Boolean writeable, Long projectNo, Map<Long, String> values) {
            super(ticketNo, ticketName, shared, status, permissions, writeable);
            this.projectNo = projectNo;
            this.values = values;
        }
    }
}
