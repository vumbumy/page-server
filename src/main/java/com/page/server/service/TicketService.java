package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.dao.TicketDao;
import com.page.server.dao.ValueDao;
import com.page.server.dto.TicketDto;
import com.page.server.entity.Permission;
import com.page.server.entity.Ticket;
import com.page.server.entity.User;
import com.page.server.repository.TicketRepository;
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
    private final UserGroupService userGroupService;

    private final ValueService valueService;

    public List<TicketDao> getPublicTicketList(Long projectNo) {
        return ticketRepository.findAllReadable(projectNo, permissionService.getPublicContentNoList());
    }

    public TicketDto.Response getPublicTicket(Long ticketNo) {
        Ticket ticket = ticketRepository.findById(ticketNo)
                .orElseThrow(() -> new IllegalArgumentException("Not found ticket."));

        if (!ticket.isReadable(null, null)) {
            throw new RuntimeException("Not shared.");
        }

        return ticketConvert.toResponse(
                ticket,
                valueService.getTicketValues(ticket)
        );
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
            List<PermissionDao.Content> pDaoList = permissionService.getPermissionDaoListByUserNo(user.userNo);

            Map<Long, AccessRight> accessRightMap = pDaoList.stream()
                    .collect(Collectors.toMap(PermissionDao.Content::getContentNo, PermissionDao::getAccessRight));

            List<TicketDao> tDaoList = ticketRepository.findAllByTicketNoContains(
                    projectNo,
                    accessRightMap.keySet(),
                    statusNum
            );

            tDaoList.forEach(ticketDao -> {
//                AccessRight accessRight = accessRightMap.get(ticketDao.getTicketNo());

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
        Ticket ticket = ticketRepository.findById(ticketNo)
                .orElseThrow(() -> new IllegalArgumentException("Not found ticket."));

        Boolean writable = permissionService.hasPermission(user, ticket);

        List<ValueDao> values = valueService.getTicketValues(ticket);

        return ticketConvert.toDetail(
                ticket,
                writable,
                values,
                permissionService.getPermissionDtoListByPermissions(ticket.permissions),
                null
        );
    }

    @Transactional
    public TicketDto.Response createTicket(User user, TicketDto.Request request) {
        Ticket ticket = ticketRepository.save(
                Ticket.builder()
                        .managerNo(user.userNo)
                        .contentName(request.ticketName)
                        .projectNo(request.projectNo)
                        .status(request.status)
                        .permissions(
                                permissionService.getDefaultPermissionList(user)
                        )
                        .build()
        );

        valueService.add(ticket.contentNo, request.values);

        return ticketConvert.toResponse(ticket);
    };

    @Transactional
    public void updateTicket(User user, TicketDto.Request request) {
        Ticket ticket = ticketRepository.findById(request.ticketNo)
                .orElseThrow(() -> new IllegalArgumentException("Not found ticket."));

        permissionService.checkPermission(user, ticket);

        ticket.contentName = request.ticketName;
        ticket.status = request.status;
        ticket.permissions = permissionService
                .addListIfNotExist(request.permissions);

        valueService.put(ticket.contentNo, request.values);

        ticketRepository.save(ticket);
    };

    public void updateTicketStatus(User user, TicketDto request) {
        Ticket ticket = ticketRepository.findById(request.ticketNo)
                .orElseThrow(() -> new IllegalArgumentException("Not found ticket."));

        permissionService.checkPermission(user, ticket);

        ticket.status = request.status;

        ticketRepository.save(ticket);
    };

    public void deleteTicket(User user, Long ticketNo) {
        Ticket ticket = ticketRepository.findById(ticketNo)
                .orElseThrow(() -> new IllegalArgumentException("Not found ticket."));

        permissionService.checkPermission(user, ticket);

        ticket.deleted = true;

        ticketRepository.save(ticket);
    }

    public void updatePermissions(User user, Long ticketNo, List<Permission> permissions) {
        Ticket ticket = ticketRepository.findById(ticketNo)
                .orElseThrow(() -> new IllegalArgumentException("Not found ticket."));

        permissionService.checkPermission(user, ticket);

        ticket.permissions = permissionService.addListIfNotExist(permissions);

        ticketRepository.save(ticket);
    }
}
