package com.page.server.repository;

import com.page.server.dao.EventDao;
import com.page.server.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT\n" +
                    "    event_type AS eventType,\n" +
                    "    param_json AS paramJson\n" +
                    "FROM\n" +
                    "    _event\n" +
                    "WHERE\n" +
                    "    scheduled IS TRUE"
    )
    List<EventDao> findAllByScheduledIsTrue();

    List<Event> findAllByScheduled(Boolean isScheduled);
}
