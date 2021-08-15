package com.page.server.service;

import com.page.server.dao.TicketDao;
import com.page.server.dto.TicketDto;
import com.page.server.entity.Permission;
import com.page.server.entity.Ticket;
import com.page.server.entity.User;
import com.page.server.repository.TicketRepository;
import com.page.server.support.TicketConvert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketConvert ticketConvert;

    private final PermissionService permissionService;

    public List<TicketDao> getPublicTicketList() {
        return ticketRepository.findPublicAll();
    }

    public List<TicketDao> getTicketListByUser(User user) {

        if (user.isAdmin()) {
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

        return ticketConvert.to(ticket);
    }

    public TicketDto createTicket(User user, TicketDto request) {
        List<Permission> permissions = permissionService.addListIfNotExist(request.permissions);
        permissions.add(
                permissionService.getDefaultPermission(user)
        );

        Ticket ticket = Ticket.builder()
                .title(request.title)
                .status(request.status)
                .content(request.content)
                .permissions(permissions)
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

        ticket.title = request.title;
        ticket.status = request.status;
        ticket.content = request.content;
        ticket.permissions = permissionService
                .addListIfNotExist(request.permissions);

        ticketRepository.save(ticket);
    };

    public void updateTicketStatus(User user, TicketDto request) {
        Ticket ticket = ticketRepository.findById(request.ticketNo).orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Not found ticket.");
        }

        if(!user.isAdmin() && !ticket.isMatch(user.getUserNo())) {
            throw new RuntimeException("Not found ticket.");
        }

        ticket.title = request.title;
        ticket.status = request.status;

        ticketRepository.save(ticket);
    };
}
