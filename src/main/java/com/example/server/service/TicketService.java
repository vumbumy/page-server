package com.example.server.service;

import com.example.server.dao.TicketDao;
import com.example.server.dto.TicketDto;
import com.example.server.entity.Permission;
import com.example.server.entity.Ticket;
import com.example.server.entity.User;
import com.example.server.repository.TicketRepository;
import com.example.server.support.TicketConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketConvert ticketConvert;

    private final PermissionService permissionService;

    public List<Ticket> getTicketAll() {
        return ticketRepository.findAll();
    }

    public List<TicketDao> getTicketListByUser(User user) {
        if(user.isAdmin()) {
            return ticketRepository.findAllTicketDaoList();
        }

        return ticketRepository.findAllByPermissions(
                permissionService.getPermissionNoListByUserNo(user.getUserNo())
        );
    }


    public TicketDto getTicketByUser(User user, Long ticketNo) {
        Ticket ticket = ticketRepository.findById(ticketNo).orElse(null);
        if (ticket == null) {
            return null;
        }

        if(!user.isAdmin() && !ticket.isMatch(user.getUserNo())) {
            return null;
        }

        return TicketDto.builder()
                .content(ticket.content)
                .status(ticket.status)
                .permissions(ticket.permissions)
                .build();
    }

    public TicketDto createTicket(TicketDto request) {
        Ticket ticket = Ticket.builder()
                .status(request.status)
                .content(request.content)
                .permissions(
                        permissionService.addListIfNotExist(request.permissions)
                )
                .build();

        return ticketConvert.to(
                ticketRepository.save(ticket)
        );
    };

    public void updateTicket(User user, TicketDto request) {
        Ticket ticket = ticketRepository.findById(request.ticketNo).orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Not found ticket.");
        }

        if(!user.isAdmin() && !ticket.isMatch(user.getUserNo())) {
            throw new RuntimeException("Not found ticket.");
        }

        ticket.status = request.status;
        ticket.content = request.content;
//        ticket.setPermissions(
//                permissionService.addListIfNotExist(request.permissions)
//        );

        ticketRepository.save(ticket);
    };
}
