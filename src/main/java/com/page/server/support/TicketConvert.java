package com.page.server.support;

import com.page.server.dto.TicketDto;
import com.page.server.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketConvert {

    @Mapping(source = "contentNo", target = "ticketNo")
    TicketDto to(Ticket ticket);
}
