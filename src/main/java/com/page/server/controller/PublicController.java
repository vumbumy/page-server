package com.page.server.controller;

import com.page.server.dao.TicketDao;
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

    @GetMapping("/tickets")
    public ResponseEntity<List<TicketDao>> getTickets() {
        return ResponseEntity.ok(
                ticketService.getPublicTicketList()
        );
    }
}
