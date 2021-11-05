package com.page.server.support;

import com.page.server.dao.CellDao;
import com.page.server.dto.PermissionDto;
import com.page.server.dto.TicketDto;
import com.page.server.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketConvert {

    @Mapping(source = "contentNo", target = "ticketNo")
    @Mapping(source = "contentName", target = "ticketName")
    TicketDto.Response toResponse(Ticket ticket);

    @Mapping(source = "ticket.contentNo", target = "ticketNo")
    @Mapping(source = "ticket.contentName", target = "ticketName")
    TicketDto.Response toResponse(Ticket ticket, List<CellDao> values);

    @Mapping(source = "ticket.contentNo", target = "ticketNo")
    @Mapping(source = "ticket.contentName", target = "ticketName")
    TicketDto.Detail toDetail(Ticket ticket, Boolean writable, List<CellDao> values, List<PermissionDto.User> userPermissions, List<PermissionDto.Group> groupPermissions);

    @Mapping(source = "ticketNo", target = "contentNo")
    @Mapping(source = "ticketName", target = "contentName")
    Ticket fromRequest(TicketDto.Request request);
}
