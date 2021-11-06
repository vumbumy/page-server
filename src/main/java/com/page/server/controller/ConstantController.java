package com.page.server.controller;

import com.page.server.entity.Event;
import com.page.server.entity.Ticket;
import com.page.server.entity.data.DataColumn;
import com.page.server.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ConstantController {
    private final RoleRepository roleRepository;

    @GetMapping("/secured/admin/roles")
    public ResponseEntity<List<String>> getRoles() {
        return ResponseEntity.ok(
                roleRepository.findAll().stream().map(role -> role.value)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/tickets/status")
    public ResponseEntity<Ticket.Status[]> getStatus() {
        return ResponseEntity.ok(
                Ticket.Status.values()
        );
    }

    @GetMapping("/types/data-types")
    public ResponseEntity<DataColumn.Type[]> getDataColumnTypes() {
        return ResponseEntity.ok(
                DataColumn.Type.values()
        );
    }

    @GetMapping("/types/events/types")
    public ResponseEntity<Event.Type[]> getEventTypes() {
        return ResponseEntity.ok(
                Event.Type.values()
        );
    }
}
