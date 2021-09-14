package com.page.server.support;

import com.page.server.dto.ProjectDto;
import com.page.server.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectConvert {

    @Mapping(source = "project.contentNo", target = "projectNo")
    @Mapping(source = "project.contentName", target = "projectName")
    @Mapping(target = "startedAt", ignore = true)
    @Mapping(target = "endedAt", ignore = true)
    ProjectDto.Detail to(Project project, Boolean writable);
}
