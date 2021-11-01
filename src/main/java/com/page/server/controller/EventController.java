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
    public ResponseEntity<Object> createEvent(@AuthenticationPrincipal User user, @RequestBody Event event) {
        eventService.createEvent(user, event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<Event>> getSchedules(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                eventService.getEventListByUser(user, Event.Status.SCHEDULE)
        );
    }
}
