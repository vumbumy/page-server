package com.page.server.controller;

import com.page.server.dao.ProjectDao;
import com.page.server.dao.TicketDao;
import com.page.server.service.ProjectService;
import com.page.server.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublicController {
    private final TicketService ticketService;
    private final ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDao>> getProjects() {
        return ResponseEntity.ok(
                projectService.getPublicProjectList()
        );
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDao>> getTickets() {
        return ResponseEntity.ok(
                ticketService.getPublicTicketList()
        );
    }
}
