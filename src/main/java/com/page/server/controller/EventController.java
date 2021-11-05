package com.page.server.controller;

import com.page.server.dto.ProjectDto;
import com.page.server.entity.Event;
import com.page.server.entity.User;
import com.page.server.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured")
public class EventController {
    private final EventService eventService;

    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@AuthenticationPrincipal User user, @RequestBody Event event) {

        return ResponseEntity.ok(
                eventService.createEvent(user, event)
        );
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<Event>> getScheduleList(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                eventService.getEventListByUser(user, Event.Type.SCHEDULE)
        );
    }

    @GetMapping("/kpis")
    public ResponseEntity<List<Event>> getKPIList(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                eventService.getEventListByUser(user, Event.Type.KPI)
        );
    }


}
