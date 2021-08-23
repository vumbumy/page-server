package com.page.server.dto;

import com.page.server.entity.DataValue;
import com.page.server.entity.Permission;
import com.page.server.entity.Ticket;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.Map;

public class TicketDto {
    public Long ticketNo;
    public String title;
    public Boolean isPublic;
    public Ticket.Status status;
    public List<Permission> permissions;
    public Boolean isWriteable;

    public TicketDto(Long ticketNo, String title, Boolean isPublic, Ticket.Status status, List<Permission> permissions, Boolean isWriteable) {
        this.ticketNo = ticketNo;
        this.title = title;
        this.isPublic = isPublic;
        this.status = status;
        this.permissions = permissions;
        this.isWriteable = isWriteable;
    }

    public static class Response extends TicketDto{
        public List<DataValue> values;

        @Builder
        public Response(Long ticketNo, String title, Boolean isPublic, Ticket.Status status, List<Permission> permissions, Boolean isWriteable, List<DataValue> values) {
            super(ticketNo, title, isPublic, status, permissions, isWriteable);
            this.values = values;
        }
    }

    public static class Request extends TicketDto {
        public Map<Long, String> values;

        @Builder
        public Request(Long ticketNo, String title, Boolean isPublic, Ticket.Status status, List<Permission> permissions, Boolean isWriteable, Map<Long, String> values) {
            super(ticketNo, title, isPublic, status, permissions, isWriteable);
            this.values = values;
        }
    }
}
