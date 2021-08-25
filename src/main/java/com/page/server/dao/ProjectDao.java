package com.page.server.dao;

import com.page.server.entity.Project;

public interface ProjectDao {
    Long getProjectNo();

    String getProjectName();

    Boolean getShared();
}
