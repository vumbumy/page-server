package com.example.server.support;

import com.example.server.dto.TicketDto;
import com.example.server.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketConvert {

    TicketDto.Response to(Ticket ticket);
}
