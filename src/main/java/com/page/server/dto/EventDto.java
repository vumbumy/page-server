package com.page.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
public class EventDto {
    public String eventName;

    public static class Response extends EventDto {
        public String action;
        public String paramJson;
        public LocalDateTime createdAt;

        @Builder
        public Response(String eventName, String action, String paramJson, LocalDateTime createdAt) {
            super(eventName);
            this.action = action;
            this.paramJson = paramJson;
            this.createdAt = createdAt;
        }
    }
}
