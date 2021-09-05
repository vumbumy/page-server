package com.page.server.controller;

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

    @GetMapping("")
    public ResponseEntity<List<TicketDto.Response>> getTickets(@AuthenticationPrincipal User user, @RequestParam(required = false) Long projectNo, @RequestParam(required = false) Ticket.Status status) {
        return ResponseEntity.ok(
                ticketService.getTicketListByUser(user, projectNo, status)
        );
    }

    @PostMapping("")
    public ResponseEntity<TicketDto.Response> createTicket(@AuthenticationPrincipal User user, @RequestBody TicketDto.Request request) {
        return ResponseEntity.ok(
                ticketService.createTicket(user, request)
        );
    }

    @GetMapping("/{ticketNo}")
    public ResponseEntity<TicketDto.Detail> getTicket(@AuthenticationPrincipal User user, @PathVariable Long ticketNo) {
        return ResponseEntity.ok(
                ticketService.getTicketByUser(user, ticketNo)
        );
    }

    @PutMapping("/status")
    public ResponseEntity<Object> updateTicketStatus(@AuthenticationPrincipal User user, @RequestBody TicketDto ticketDto) {
        ticketService.updateTicketStatus(user, ticketDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Object> updateTicket(@AuthenticationPrincipal User user, @RequestBody TicketDto.Request request) {
        ticketService.updateTicket(user, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{ticketNo}")
    public ResponseEntity<Object> deleteTicket(@AuthenticationPrincipal User user, @PathVariable Long ticketNo) {
        ticketService.deleteTicket(user, ticketNo);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
