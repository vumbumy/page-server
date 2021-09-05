package com.page.server.dto;

import com.page.server.dao.ValueDao;
import com.page.server.entity.Permission;
import com.page.server.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class TicketDto {
    public Long ticketNo;
    public String ticketName;
    public Boolean shared;
    public Ticket.Status status;
    public List<Permission> permissions;
    public Boolean writeable;

    public static class Detail extends TicketDto{
        public List<ValueDao> values;

        @Builder
        public Detail(Long ticketNo, String ticketName, Boolean shared, Ticket.Status status, List<Permission> permissions, Boolean writeable, List<ValueDao> values) {
            super(ticketNo, ticketName, shared, status, permissions, writeable);
            this.values = values;
        }
    }

    public static class Response extends TicketDto{

        @Builder
        public Response(Long ticketNo, String ticketName, Boolean shared, Ticket.Status status, List<Permission> permissions, Boolean writeable) {
            super(ticketNo, ticketName, shared, status, permissions, writeable);
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
