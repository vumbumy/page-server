package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.dao.ProjectDao;
import com.page.server.dto.ProjectDto;
import com.page.server.entity.Permission;
import com.page.server.entity.Project;
import com.page.server.entity.Type;
import com.page.server.entity.User;
import com.page.server.repository.ProjectRepository;
import com.page.server.support.ProjectConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectConvert projectConvert;

    private final PermissionService permissionService;
    private final TypeService typeService;

    public List<ProjectDao> getPublicProjectList() {
        return projectRepository.findAllShared();
    }

    public List<ProjectDto.Response> getProjectListByUser(User user) {
       List<ProjectDto.Response> dtoList = new ArrayList<>();
        if (user.isAdmin()) {
            List<ProjectDao> daoList = projectRepository.findProjectDaoAll();

            daoList.forEach(projectDao -> dtoList.add(ProjectDto.Response.builder()
                    .projectNo(projectDao.getProjectNo())
                    .projectName(projectDao.getProjectName())
                    .writeable(Boolean.TRUE)
                    .build())
            );
        } else {
            List<PermissionDao> pDaoList = permissionService.getPermissionDaoListByUserNo(user.getUserNo());

            Map<Long, AccessRight> accessRightMap = pDaoList.stream()
                    .collect(Collectors.toMap(PermissionDao::getContentNo, PermissionDao::getAccessRight));

            List<ProjectDao> tDaoList = projectRepository.findAllByProjectNoContains(
                    accessRightMap.keySet()
            );

            tDaoList.forEach(projectDao -> {
                AccessRight accessRight = accessRightMap.get(projectDao.getProjectNo());

                dtoList.add(ProjectDto.Response.builder()
                        .projectNo(projectDao.getProjectNo())
                        .projectName(projectDao.getProjectName())
                        .writeable(
                                accessRight != null && accessRight.equals(AccessRight.WRITE)
                        )
                        .build());
            });
        }

        return dtoList;
    }


    public ProjectDto.Detail getProjectByUser(User user, Long projectNo) {
        Project project = projectRepository.findById(projectNo).orElse(null);
        if (project == null) {
            return null;
        }

        if(!user.isAdmin() && !project.isReadable(user.getUserNo(), user.getGroupNo())) {
            return null;
        }

        return projectConvert.to(project);
    }

    public ProjectDto.Detail createProject(User user, ProjectDto.Request request) {

        List<Permission> permissions = permissionService.addListIfNotExist(request.permissions);
        List<Type> types = typeService.addListIfNotExist(request.types);

        Project project = Project.builder()
                .managerNo(user.getUserNo())
                .contentName(request.projectName)
                .permissions(permissions)
                .types(types)
                .shared(request.shared)
                .build();

        return projectConvert.to(
                projectRepository.save(project)
        );
    };

    public void updateProject(User user, ProjectDto.Request request) {
        Project project = projectRepository.findById(request.projectNo).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Not found project.");
        }

        if(!user.isAdmin() && !project.isWriteable(user.getUserNo(), user.getGroupNo())) {
            throw new RuntimeException("You don't have permission.");
        }

        project.contentName = request.projectName;
        project.permissions = permissionService
                .addListIfNotExist(request.permissions);
        project.shared = request.shared;

        projectRepository.save(project);
    };

    public void updateProjectStatus(User user, ProjectDto request) {
        Project project = projectRepository.findById(request.projectNo).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Not found project.");
        }

        if(!user.isAdmin() && !project.isWriteable(user.getUserNo(), user.getGroupNo())) {
            throw new RuntimeException("You don't have permission.");
        }

        project.contentName = request.projectName;

        projectRepository.save(project);
    };

    public void deleteProject(User user, Long projectNo) {
        Project project = projectRepository.findById(projectNo).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Not found project.");
        }

        if(!user.isAdmin() && !project.isWriteable(user.getUserNo(), user.getGroupNo())) {
            throw new RuntimeException("Not found project.");
        }

        project.deleted = true;

        projectRepository.save(project);
    }
}