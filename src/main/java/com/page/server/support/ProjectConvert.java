package com.page.server.support;

import com.page.server.dto.ProjectDto;
import com.page.server.entity.Project;
import com.page.server.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectConvert {

    @Mapping(source = "project.contentNo", target = "projectNo")
    @Mapping(source = "project.contentName", target = "projectName")
    ProjectDto.Detail to(Project project, List<Type> types, Boolean writable);
}
