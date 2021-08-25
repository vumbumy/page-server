package com.page.server.dto;

import com.page.server.entity.Type;
import com.page.server.entity.Permission;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;

public class ProjectDto {
    public Long projectNo;
    public String projectName;
    public Boolean shared;

    public ProjectDto(Long projectNo, String projectName, Boolean shared) {
        this.projectNo = projectNo;
        this.projectName = projectName;
        this.shared = shared;
    }

    public static class Response extends ProjectDto {

        public Boolean writeable;

        @Builder
        public Response(Long projectNo, String projectName, Boolean shared, Boolean writeable) {
            super(projectNo, projectName, shared);
            this.writeable = writeable;
        }
    }

    public static class Detail extends ProjectDto {

        public Boolean writeable;
        public String description;
        public Long startedAt;
        public Long endedAt;

        public List<Permission> permissions;
        public List<Type> types;

        @Builder
        public Detail(Long projectNo, String projectName, Boolean shared, Boolean writeable, String description, Long startedAt, Long endedAt, List<Permission> permissions, List<Type> types) {
            super(projectNo, projectName, shared);
            this.writeable = writeable;
            this.description = description;
            this.startedAt = startedAt;
            this.endedAt = endedAt;
            this.permissions = permissions;
            this.types = types;
        }
    }

    public static class Request extends ProjectDto {

        public String description;
        public Long startedAt;
        public Long endedAt;

        public List<Permission> permissions;
        public List<Type> types;

        @Builder
        public Request(Long projectNo, String projectName, Boolean shared, String description, Long startedAt, Long endedAt, List<Permission> permissions, List<Type> types) {
            super(projectNo, projectName, shared);
            this.description = description;
            this.startedAt = startedAt;
            this.endedAt = endedAt;
            this.permissions = permissions;
            this.types = types;
        }
    }
}
