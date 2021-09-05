package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.dao.TicketDao;
import com.page.server.dto.TicketDto;
import com.page.server.entity.*;
import com.page.server.repository.TicketRepository;
import com.page.server.repository.TypeRepository;
import com.page.server.repository.ValueRepository;
import com.page.server.support.TicketConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    private final TypeRepository typeRepository;
    private final ValueRepository valueRepository;

    public List<TicketDao> getPublicTicketList(Long projectNo) {
        return ticketRepository.findAllShared(projectNo);
    }

    public List<TicketDto.Response> getTicketListByUser(User user, Long projectNo, Ticket.Status status) {
        Integer statusNum = status != null ? status.ordinal() : null;

        List<TicketDto.Response> dtoList = new ArrayList<>();
        if (user.isAdmin()) {
            List<TicketDao> daoList = ticketRepository.findAllTicketDaoList(projectNo, statusNum);

            daoList.forEach(ticketDao -> dtoList.add(TicketDto.Response.builder()
                    .ticketNo(ticketDao.getTicketNo())
                    .ticketName(ticketDao.getTicketName())
                    .status(ticketDao.getStatus())
                    .build())
            );
        } else {
            List<PermissionDao> pDaoList = permissionService.getPermissionDaoListByUserNo(user.getUserNo());

            Map<Long, AccessRight> accessRightMap = pDaoList.stream()
                    .collect(Collectors.toMap(PermissionDao::getContentNo, PermissionDao::getAccessRight));

            List<TicketDao> tDaoList = ticketRepository.findAllByTicketNoContains(
                    projectNo,
                    accessRightMap.keySet(),
                    statusNum
            );

            tDaoList.forEach(ticketDao -> {
                AccessRight accessRight = accessRightMap.get(ticketDao.getTicketNo());

                dtoList.add(TicketDto.Response.builder()
                        .ticketNo(ticketDao.getTicketNo())
                        .ticketName(ticketDao.getTicketName())
                        .status(ticketDao.getStatus())
//                        .writable(
//                                accessRight != null && accessRight.equals(AccessRight.WRITE)
//                        )
                        .build());
            });
        }

        return dtoList;
    }


    public TicketDto.Detail getTicketByUser(User user, Long ticketNo) {
        Ticket ticket = ticketRepository.findById(ticketNo).orElse(null);
        if (ticket == null) {
            return null;
        }

        boolean writable = user.isAdmin();
        if(!writable) {
            if(!ticket.isReadable(user.getUserNo(), user.getGroupNo())) {
                return null;
            }

            PermissionDao permissionDao = permissionService.getPermissionDaoByUserNo(user.getUserNo(), ticketNo);

            writable = permissionDao != null && permissionDao.getAccessRight().equals(AccessRight.WRITE);
        }

        return ticketConvert.toDetail(
                ticket, writable, valueRepository.findAllByContentNo(ticketNo)
        );
    }

    @Transactional
    public TicketDto.Response createTicket(User user, TicketDto.Request request) {

        List<Permission> permissions = permissionService.addListIfNotExist(request.permissions);

        Ticket ticket = ticketRepository.save(
                Ticket.builder()
                        .managerNo(user.getUserNo())
                        .contentName(request.ticketName)
                        .projectNo(request.projectNo)
                        .status(request.status)
                        .permissions(permissions)
                        .shared(request.shared)
                        .build()
        );

        List<Value> valueList = new ArrayList<>();
        request.values.forEach((typeNo, value) -> {
            Type type = typeRepository.findById(typeNo).orElseThrow(
                    () -> new RuntimeException("Not Fount Type: " + typeNo)
            );

            valueList.add(Value.builder()
                    .contentNo(ticket.contentNo)
                    .type(type)
                    .dataValue(value)
                    .build()
            );
        });

        valueRepository.saveAll(valueList);

        return ticketConvert.toResponse(ticket);
    };

    @Transactional
    public void updateTicket(User user, TicketDto.Request request) {
        Ticket ticket = ticketRepository.findById(request.ticketNo).orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Not found ticket.");
        }

        if(!user.isAdmin() && !ticket.iswritable(user.getUserNo(), user.getGroupNo())) {
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

        if(!user.isAdmin() && !ticket.iswritable(user.getUserNo(), user.getGroupNo())) {
            throw new RuntimeException("You don't have permission.");
        }

        ticket.status = request.status;
        ticket.shared = request.shared;

        ticketRepository.save(ticket);
    };

    public void deleteTicket(User user, Long ticketNo) {
        Ticket ticket = ticketRepository.findById(ticketNo).orElse(null);
        if (ticket == null) {
            throw new IllegalArgumentException("Not found ticket.");
        }

        if(!user.isAdmin() && !ticket.iswritable(user.getUserNo(), user.getGroupNo())) {
            throw new RuntimeException("Not found ticket.");
        }

        ticket.deleted = true;

        ticketRepository.save(ticket);
    }
}
