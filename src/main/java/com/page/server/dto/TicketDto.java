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
    public Ticket.Status status;

    public Boolean readable;
    public Boolean writable;

    public static class Detail extends TicketDto{
        public List<ValueDao> values;
        public List<Permission> permissions;

        @Builder
        public Detail(Long ticketNo, String ticketName, Ticket.Status status, Boolean readable, Boolean writable, List<ValueDao> values, List<Permission> permissions) {
            super(ticketNo, ticketName, status, readable, writable);
            this.values = values;
            this.permissions = permissions;
        }
    }

    public static class Response extends TicketDto{

        @Builder
        public Response(Long ticketNo, String ticketName, Ticket.Status status, Boolean readable, Boolean writable) {
            super(ticketNo, ticketName, status, readable, writable);
        }
    }

    public static class Request extends TicketDto {
        public Long projectNo;
        public Map<Long, String> values;
        public List<Permission> permissions;

        @Builder
        public Request(Long ticketNo, String ticketName, Ticket.Status status, Boolean readable, Boolean writable, Long projectNo, Map<Long, String> values, List<Permission> permissions) {
            super(ticketNo, ticketName, status, readable, writable);
            this.projectNo = projectNo;
            this.values = values;
            this.permissions = permissions;
        }
    }
}
