package com.example.server.controller;

import com.example.server.dao.TicketDao;
import com.example.server.service.TicketService;
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
