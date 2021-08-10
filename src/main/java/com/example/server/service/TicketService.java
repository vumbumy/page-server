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


    public TicketDto.Response getTicketByUser(User user, Long ticketNo) {
        Ticket ticket = ticketRepository.findById(ticketNo).orElse(null);
        if (ticket == null) {
            return null;
        }

        if(!user.isAdmin() && !ticket.isMatch(user.getUserNo())) {
            return null;
        }

        return TicketDto.Response.builder()
                .content(ticket.content)
                .status(ticket.status)
                .permissions(ticket.getPermissions())
                .build();
    }

    public TicketDto.Response createTicket(TicketDto.Request request) {

        List<Permission> permissionList = new ArrayList<>();
        for(Permission permission : request.permissions) {
            permissionList.add(
                    permissionService.createIfNotExist(permission)
            );
        }

        return ticketConvert.to(
                ticketRepository.save(
                        Ticket.builder()
                                .content(request.content)
                                .permissions(permissionList)
                                .build()
                )
        );
    };
}
