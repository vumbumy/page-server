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

    public static class Detail extends TicketDto{
        public List<ValueDao> values;
        public List<PermissionDto.User> userPermissions;
        public List<PermissionDto.Group> groupPermissions;

        public Boolean writable;

        @Builder
        public Detail(Long ticketNo, String ticketName, Ticket.Status status, List<ValueDao> values, List<PermissionDto.User> userPermissions, List<PermissionDto.Group> groupPermissions, Boolean writable) {
            super(ticketNo, ticketName, status);
            this.values = values;
            this.userPermissions = userPermissions;
            this.groupPermissions = groupPermissions;
            this.writable = writable;
        }
    }

    public static class Response extends TicketDto{
        public List<ValueDao> values;

        @Builder
        public Response(Long ticketNo, String ticketName, Ticket.Status status, List<ValueDao> values) {
            super(ticketNo, ticketName, status);
            this.values = values;
        }
    }

    public static class Request extends TicketDto {
        public Long projectNo;
        public Map<Long, String> values;
        public List<Permission> permissions;

        @Builder
        public Request(Long ticketNo, String ticketName, Ticket.Status status, Long projectNo, Map<Long, String> values, List<Permission> permissions) {
            super(ticketNo, ticketName, status);
            this.projectNo = projectNo;
            this.values = values;
            this.permissions = permissions;
        }
    }
}
