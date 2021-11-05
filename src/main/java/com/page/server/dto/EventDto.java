package com.page.server.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
public class EventDto {
    public String eventName;

    @Builder(access = AccessLevel.PRIVATE)
    public static class Response extends EventDto {
        public String action;
        public String paramJson;
        public LocalDateTime createdAt;
    }

    public static class Result extends Response {
        public String result;

        @Builder
        public Result(String action, String paramJson, LocalDateTime createdAt, String result) {
            super(action, paramJson, createdAt);
            this.result = result;
        }
    }
}
