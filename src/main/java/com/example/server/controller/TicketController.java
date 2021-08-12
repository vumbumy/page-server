package com.example.server.controller;

import com.example.server.dao.TicketDao;
import com.example.server.dto.TicketDto;
import com.example.server.entity.Ticket;
import com.example.server.entity.User;
import com.example.server.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/status")
    public ResponseEntity<Ticket.Status[]> getStatus() {
        return ResponseEntity.ok(
                Ticket.Status.values()
        );
    }

    @GetMapping("")
    public ResponseEntity<List<TicketDao>> getTickets(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                ticketService.getTicketListByUser(user)
        );
    }

    @PostMapping("")
    public ResponseEntity<TicketDto> createTicket(@AuthenticationPrincipal User user, @RequestBody TicketDto request) {
        return ResponseEntity.ok(
                ticketService.createTicket(request)
        );
    }

    @GetMapping("/{ticketNo}")
    public ResponseEntity<TicketDto> getTicket(@AuthenticationPrincipal User user, @PathVariable Long ticketNo) {
        return ResponseEntity.ok(
                ticketService.getTicketByUser(user, ticketNo)
        );
    }

    @PutMapping("")
    public ResponseEntity<Object> updateTicket(@AuthenticationPrincipal User user, @RequestBody TicketDto request) {
        ticketService.updateTicket(user, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
