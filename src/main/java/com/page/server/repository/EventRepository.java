package com.page.server.repository;

import com.page.server.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByEventTypeAndEnabledIsTrue(Event.Type eventType);

    List<Event> findAllByEventType(Event.Type eventType);
}
