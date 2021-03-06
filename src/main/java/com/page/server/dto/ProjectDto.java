package com.page.server.dto;

import com.page.server.entity.data.DataColumn;
import com.page.server.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class ProjectDto {
    public Long projectNo;
    public String projectName;

    public static class Response extends ProjectDto {

        public String managerName;
        public Boolean writable;
        public Integer ticketCount;
        public LocalDateTime createdAt;

        @Builder
        public Response(Long projectNo, String projectName, String managerName, Boolean writable, Integer ticketCount, LocalDateTime createdAt) {
            super(projectNo, projectName);
            this.managerName = managerName;
            this.writable = writable;
            this.ticketCount = ticketCount;
            this.createdAt = createdAt;
        }
    }

    public static class Detail extends ProjectDto {
        public Boolean writable;
        public String description;
        public Long startedAt;
        public Long endedAt;

        public List<Permission> permissions;
        public List<DataColumn> columns;

        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;

        @Builder
        public Detail(Long projectNo, String projectName, Boolean writable, String description, Long startedAt, Long endedAt, List<Permission> permissions, List<DataColumn> columns, LocalDateTime createdAt, LocalDateTime updatedAt) {
            super(projectNo, projectName);
            this.writable = writable;
            this.description = description;
            this.startedAt = startedAt;
            this.endedAt = endedAt;
            this.permissions = permissions;
            this.columns = columns;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }

    public static class Request extends ProjectDto {
        public String description;
        public Long startedAt;
        public Long endedAt;

        public List<Permission> permissions;
        public List<DataColumn> columns;

        @Builder
        public Request(Long projectNo, String projectName, String description, Long startedAt, Long endedAt, List<Permission> permissions, List<DataColumn> columns) {
            super(projectNo, projectName);
            this.description = description;
            this.startedAt = startedAt;
            this.endedAt = endedAt;
            this.permissions = permissions;
            this.columns = columns;
        }
    }
}
