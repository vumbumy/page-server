package com.page.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.page.server.dao.EventDao;
import com.page.server.dto.EventDto;
import com.page.server.entity.Event;
import com.page.server.entity.User;
import com.page.server.model.form.NotificationParam;
import com.page.server.model.form.UpdateParam;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(cron="${cron.rule}")
    public void eventScheduler() {
        List<EventDao> eventList = eventRepository.findAllByScheduledIsTrue();
        eventList.forEach(eventDao -> {
            try {
                switch (eventDao.getEventType()) {
                    case NOTIFICATION:
                        NotificationParam ntParam = objectMapper.readValue(
                                eventDao.getParamJson(),
                                NotificationParam.class
                        );

                        log.info("{}", ntParam);
                        break;
                    case UPDATE:
                        UpdateParam udtParam = objectMapper.readValue(
                                eventDao.getParamJson(),
                                UpdateParam.class
                        );

                        log.info("{}", udtParam);
                        break;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    public Event createEvent(User user, Event event) {
        event.managerNo = user.userNo;
        event.permissions = permissionService.getDefaultPermissionList(user);

        return eventRepository.save(event);
    }

    public List<Event> getEventListByUser(User user, Boolean isScheduled) {
        if (user.isAdmin()) {
            return eventRepository.findAllByScheduled(isScheduled);
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
        List<Event> eventList = getEventListByUser(user, Boolean.FALSE);

        List<EventDto.Result> resultList = new ArrayList<>();

        eventList.forEach(event -> {
            resultList.add(
                    EventDto.Result.builder()
                            .eventType(event.eventType.getName())
                            .paramJson(event.paramJson)
                            .createdAt(event.createdAt)
                            .result("Done")
                            .build()
            );
        });

        return resultList;
    }
}
