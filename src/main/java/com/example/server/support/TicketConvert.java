package com.example.server.support;

import com.example.server.dto.TicketDto;
import com.example.server.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketConvert {

    @Mapping(source = "contentNo", target = "ticketNo")
    TicketDto to(Ticket ticket);
}
