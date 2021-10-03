package com.page.server.controller;

import com.page.server.dao.ProjectDao;
import com.page.server.dao.TicketDao;
import com.page.server.dto.TicketDto;
import com.page.server.entity.Type;
import com.page.server.repository.TypeRepository;
import com.page.server.service.ProjectService;
import com.page.server.service.TicketService;
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
    private final TicketService ticketService;
    private final ProjectService projectService;

    private final TypeRepository typeRepository;

//    @GetMapping("/projects")
//    public ResponseEntity<List<ProjectDao>> getProjects() {
//        return ResponseEntity.ok(
//                projectService.getPublicProjectList()
//        );
//    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDao>> getTickets(@RequestParam(required = false) Long projectNo) {
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
    public ResponseEntity<List<Type>> getTypeList() {
        return ResponseEntity.ok(
                typeRepository.findAllByDeletedFalseOrDeletedIsNull()
        );
    }
}
