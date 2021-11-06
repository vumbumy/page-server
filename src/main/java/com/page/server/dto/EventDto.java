package com.page.server.dto;

import com.page.server.entity.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
public class EventDto {
    public String eventName;

    public static class Response extends EventDto {
        public Event.Type eventType;
        public String paramJson;
        public LocalDateTime createdAt;

        @Builder(access = AccessLevel.PRIVATE)
        public Response(String eventName, Event.Type eventType, String paramJson, LocalDateTime createdAt) {
            super(eventName);
            this.eventType = eventType;
            this.paramJson = paramJson;
            this.createdAt = createdAt;
        }
    }

    public static class Result extends Response {
        public String result;

        @Builder
        public Result(String eventName, Event.Type eventType, String paramJson, LocalDateTime createdAt, String result) {
            super(eventName, eventType, paramJson, createdAt);
            this.result = result;
        }
    }
}
