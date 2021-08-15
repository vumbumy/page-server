package com.page.server.controller;

import com.page.server.dao.TicketDao;
import com.page.server.dto.TicketDto;
import com.page.server.entity.Ticket;
import com.page.server.entity.User;
import com.page.server.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
                ticketService.createTicket(user, request)
        );
    }

    @GetMapping("/{ticketNo}")
    public ResponseEntity<TicketDto> getTicket(@AuthenticationPrincipal User user, @PathVariable Long ticketNo) {
        return ResponseEntity.ok(
                ticketService.getTicketByUser(user, ticketNo)
        );
    }

    @PutMapping("/status")
    public ResponseEntity<Object> updateTicketStatus(@AuthenticationPrincipal User user, @RequestBody TicketDto request) {
        ticketService.updateTicketStatus(user, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateTicket(@AuthenticationPrincipal User user, @RequestBody TicketDto request) {
        ticketService.updateTicket(user, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
