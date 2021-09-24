package com.page.server.support;

import com.page.server.dao.ValueDao;
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
    TicketDto.Response toResponse(Ticket ticket, List<ValueDao> values);

    @Mapping(source = "ticket.contentNo", target = "ticketNo")
    @Mapping(source = "ticket.contentName", target = "ticketName")
    TicketDto.Detail toDetail(Ticket ticket, Boolean writable, List<ValueDao> values);

    @Mapping(source = "ticketNo", target = "contentNo")
    @Mapping(source = "ticketName", target = "contentName")
    Ticket fromRequest(TicketDto.Request request);
}
