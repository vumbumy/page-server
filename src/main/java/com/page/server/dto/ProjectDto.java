package com.page.server.dto;

import com.page.server.entity.Type;
import com.page.server.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ProjectDto {
    public Long projectNo;
    public String projectName;

    public static class Response extends ProjectDto {

        public String managerName;
        public Boolean writeable;
        public Integer ticketCount;
        public LocalDateTime createdAt;

        @Builder
        public Response(Long projectNo, String projectName, String managerName, Boolean writeable, Integer ticketCount, LocalDateTime createdAt) {
            super(projectNo, projectName);
            this.managerName = managerName;
            this.writeable = writeable;
            this.ticketCount = ticketCount;
            this.createdAt = createdAt;
        }
    }

    public static class Detail extends ProjectDto {

        public Boolean shared;
        public Boolean writeable;
        public String description;
        public Long startedAt;
        public Long endedAt;

        public List<Permission> permissions;
        public List<Type> types;

        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;

        @Builder
        public Detail(Long projectNo, String projectName, Boolean shared, Boolean writeable, String description, Long startedAt, Long endedAt, List<Permission> permissions, List<Type> types, LocalDateTime createdAt, LocalDateTime updatedAt) {
            super(projectNo, projectName);
            this.shared = shared;
            this.writeable = writeable;
            this.description = description;
            this.startedAt = startedAt;
            this.endedAt = endedAt;
            this.permissions = permissions;
            this.types = types;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }

    public static class Request extends ProjectDto {

        public Boolean shared;
        public String description;
        public Long startedAt;
        public Long endedAt;

        public List<Permission> permissions;
        public List<Type> types;

        @Builder
        public Request(Long projectNo, String projectName, Boolean shared, String description, Long startedAt, Long endedAt, List<Permission> permissions, List<Type> types) {
            super(projectNo, projectName);
            this.shared = shared;
            this.description = description;
            this.startedAt = startedAt;
            this.endedAt = endedAt;
            this.permissions = permissions;
            this.types = types;
        }
    }
}
