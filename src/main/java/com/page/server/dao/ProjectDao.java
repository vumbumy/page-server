package com.page.server.dao;

import java.time.LocalDateTime;

public interface ProjectDao {
    Long getProjectNo();

    String getProjectName();

    String getManagerName();

    Integer getTicketCount();

    LocalDateTime getCreatedAt();
}
