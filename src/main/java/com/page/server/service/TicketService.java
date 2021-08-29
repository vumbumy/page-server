package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.dao.TicketDao;
import com.page.server.dto.TicketDto;
import com.page.server.entity.Permission;
import com.page.server.entity.Ticket;
import com.page.server.entity.User;
import com.page.server.repository.TicketRepository;
import com.page.server.support.TicketConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketConvert ticketConvert;

    private final PermissionService permissionService;

    public List<TicketDao> getPublicTicketList() {
        return ticketRepository.findAllShared();
    }

    public List<TicketDto.Response> getTicketListByUser(User user, Ticket.Status status) {
        Integer statusNum = status != null ? status.ordinal() : null;

        List<TicketDto.Response> dtoList = new ArrayList<>();
        if (user.isAdmin()) {
            List<TicketDao> daoList = ticketRepository.findAllTicketDaoList(statusNum);

            daoList.forEach(ticketDao -> dtoList.add(TicketDto.Response.builder()
                    .ticketNo(ticketDao.getTicketNo())
                    .ticketName(ticketDao.getTicketName())
//                    .content(ticketDao.getContent())
                    .status(ticketDao.getStatus())
                    .writeable(Boolean.TRUE)
                    .build())
            );
        } else {
            List<PermissionDao> pDaoList = permissionService.getPermissionDaoListByUserNo(user.getUserNo());

            Map<Long, AccessRight> accessRightMap = pDaoList.stream()
                    .collect(Collectors.toMap(PermissionDao::getContentNo, PermissionDao::getAccessRight));

            List<TicketDao> tDaoList = ticketRepository.findAllByTicketNoContains(
                    accessRightMap.keySet(),
                    statusNum
            );

            tDaoList.forEach(ticketDao -> {
                AccessRight accessRight = accessRightMap.get(ticketDao.getTicketNo());

                dtoList.add(TicketDto.Response.builder()
                        .ticketNo(ticketDao.getTicketNo())
                        .ticketName(ticketDao.getTicketName())
//                        .content(ticketDao.getContent())
                        .status(ticketDao.getStatus())
                        .writeable(
                                accessRight != null && accessRight.equals(AccessRight.WRITE)
                        )
                        .build());
            });
        }

        return dtoList;
    }


    public TicketDto.Response getTicketByUser(User user, Long ticketNo) {
        Ticket ticket = ticketRepository.findById(ticketNo).orElse(null);
        if (ticket == null) {
            return null;
        }

        if(!user.isAdmin() && !ticket.isReadable(user.getUserNo(), user.getGroupNo())) {
            return null;
        }

        return ticketConvert.to(ticket);
    }

    public TicketDto.Response createTicket(User user, TicketDto.Request request) {

        List<Permission> permissions = permissionService.addListIfNotExist(request.permissions);

        Ticket ticket = Ticket.builder()
                .managerNo(user.getUserNo())
                .contentName(request.ticketName)
                .projectNo(request.projectNo)
                .status(request.status)
//                .content(request.content)
                .permissions(permissions)
                .shared(request.shared)
                .build();

        return ticketConvert.to(
                ticketRepository.save(ticket)
        );
    };

    public void updateTicket(User user, TicketDto.Request request) {
        Ticket ticket = ticketRepository.findById(request.ticketNo).orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Not found ticket.");
        }

        if(!user.isAdmin() && !ticket.isWriteable(user.getUserNo(), user.getGroupNo())) {
            throw new RuntimeException("You don't have permission.");
        }

        ticket.contentName = request.ticketName;
        ticket.status = request.status;
//        ticket.content = request.content;
        ticket.permissions = permissionService
                .addListIfNotExist(request.permissions);
        ticket.shared = request.shared;

        ticketRepository.save(ticket);
    };

    public void updateTicketStatus(User user, TicketDto request) {
        Ticket ticket = ticketRepository.findById(request.ticketNo).orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Not found ticket.");
        }

        if(!user.isAdmin() && !ticket.isWriteable(user.getUserNo(), user.getGroupNo())) {
            throw new RuntimeException("You don't have permission.");
        }

        ticket.contentNo = request.ticketNo;
        ticket.contentName = request.ticketName;
        ticket.status = request.status;

        ticketRepository.save(ticket);
    };

    public void deleteTicket(User user, Long ticketNo) {
        Ticket ticket = ticketRepository.findById(ticketNo).orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Not found ticket.");
        }

        if(!user.isAdmin() && !ticket.isWriteable(user.getUserNo(), user.getGroupNo())) {
            throw new RuntimeException("Not found ticket.");
        }

        ticket.deleted = true;

        ticketRepository.save(ticket);
    }
}
