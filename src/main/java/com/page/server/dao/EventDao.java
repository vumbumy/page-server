package com.page.server.dao;

import com.page.server.entity.Event;

public interface EventDao {
    Event.Type getEventType();

    String getParamJson();
}
