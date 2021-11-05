package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.dao.ProjectDao;
import com.page.server.dto.ProjectDto;
import com.page.server.entity.Event;
import com.page.server.entity.User;
import com.page.server.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    private final PermissionService permissionService;

    @Scheduled(cron="${cron.rule}")
    public void eventScheduler() {
        List<Event> eventList = eventRepository.findAllByEventTypeAndEnabledIsTrue(Event.Type.SCHEDULE);

        log.info("Scheduled {}", eventList.size());
    }

    public Event createEvent(User user, Event event) {
        event.managerNo = user.userNo;
        event.permissions = permissionService.getDefaultPermissionList(user);

        return eventRepository.save(event);
    }

    public List<Event> getEventListByUser(User user, Event.Type eventType) {
        if (user.isAdmin()) {
            return eventRepository.findAllByEventType(eventType);
        }

        // TODO: Get event list with checking permissions

        return new ArrayList<>();
    }
}
