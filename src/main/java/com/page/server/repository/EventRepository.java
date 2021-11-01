package com.page.server.repository;

import com.page.server.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByStatusIs(Event.Status eventStatus);
}
