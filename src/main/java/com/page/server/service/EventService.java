package com.page.server.service;

import com.page.server.dto.EventDto;
import com.page.server.entity.Event;
import com.page.server.entity.User;
import com.page.server.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
//        else {
//            List<PermissionDao.Content> pDaoList = permissionService.getPermissionDaoListByUserNo(user.userNo);
//
//            Map<Long, AccessRight> accessRightMap = pDaoList.stream()
//                    .collect(Collectors.toMap(PermissionDao.Content::getContentNo, PermissionDao::getAccessRight));
//        }

        return new ArrayList<>();
    }

    public List<EventDto.Result> getKpiResultList(User user) {
        List<Event> eventList = getEventListByUser(user, Event.Type.KPI);

        List<EventDto.Result> resultList = new ArrayList<>();

        eventList.forEach(event -> {
            resultList.add(
                    EventDto.Result.builder()
                            .action(event.action.getName())
                            .paramJson(event.paramJson)
                            .createdAt(event.createdAt)
                            .result("Done")
                            .build()
            );
        });

        return resultList;
    }
}
