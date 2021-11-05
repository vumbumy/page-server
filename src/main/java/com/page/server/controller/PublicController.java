package com.page.server.controller;

import com.page.server.dao.TicketDao;
import com.page.server.dao.UserDao;
import com.page.server.dao.UserGroupDao;
import com.page.server.dto.TicketDto;
import com.page.server.entity.data.DataColumn;
import com.page.server.repository.TypeRepository;
import com.page.server.service.TicketService;
import com.page.server.service.UserGroupService;
import com.page.server.service.UserSevice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicController {
    private final UserSevice userSevice;
    private final UserGroupService userGroupService;

    private final TicketService ticketService;

    private final TypeRepository typeRepository;

//    private final ProjectService projectService;

//    @GetMapping("/projects")
//    public ResponseEntity<List<ProjectDao>> getProjects() {
//        return ResponseEntity.ok(
//                projectService.getPublicProjectList()
//        );
//    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDao>> getTicketList(@RequestParam(required = false) Long projectNo) {
        return ResponseEntity.ok(
                ticketService.getPublicTicketList(projectNo)
        );
    }

    @GetMapping("/tickets/{ticketNo}")
    public ResponseEntity<TicketDto.Response> getTicket(@PathVariable Long ticketNo) {
        return ResponseEntity.ok(
                ticketService.getPublicTicket(ticketNo)
        );
    }

    @GetMapping("/types")
    public ResponseEntity<List<DataColumn>> getTypeList() {
        return ResponseEntity.ok(
                typeRepository.findAllByDeletedFalseOrDeletedIsNull()
        );
    }

    @GetMapping("/secured/users")
    public ResponseEntity<List<UserDao>> getUserList() {
        return ResponseEntity.ok(
                userSevice.getAllUserDaoList()
        );
    }

    @GetMapping("/secured/groups")
    public ResponseEntity<List<UserGroupDao>> getGroupList() {
        return ResponseEntity.ok(
                userGroupService.getAllUserGroupList()
        );
    }
}
