package com.example.server.dto;

import com.example.server.entity.Permission;
import com.example.server.entity.Ticket;
import lombok.Builder;

import java.util.List;

public class TicketDto {
    public static class Request {
        public String content;
        public Ticket.Status status;
        public List<Permission> permissions;
    }

    @Builder
    public static class Response {
        public Long ticketNo;
        public String content;
        public Ticket.Status status;
        public List<PermissionDto> permissions;
    }
}
