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
    public Boolean readable;
    public Ticket.Status status;

    public static class Detail extends TicketDto{
        public List<ValueDao> values;
        public List<Permission> permissions;
        public Boolean writable;

        @Builder
        public Detail(Long ticketNo, String ticketName, Boolean readable, Ticket.Status status, List<ValueDao> values, List<Permission> permissions, Boolean writable) {
            super(ticketNo, ticketName, readable, status);
            this.values = values;
            this.permissions = permissions;
            this.writable = writable;
        }
    }

    public static class Response extends TicketDto{

        @Builder
        public Response(Long ticketNo, String ticketName, Boolean readable, Ticket.Status status) {
            super(ticketNo, ticketName, readable, status);
        }
    }

    public static class Request extends TicketDto {
        public Long projectNo;
        public Map<Long, String> values;
        public List<Permission> permissions;

        @Builder
        public Request(Long ticketNo, String ticketName, Boolean readable, Ticket.Status status, Long projectNo, Map<Long, String> values, List<Permission> permissions) {
            super(ticketNo, ticketName, readable, status);
            this.projectNo = projectNo;
            this.values = values;
            this.permissions = permissions;
        }
    }
}
