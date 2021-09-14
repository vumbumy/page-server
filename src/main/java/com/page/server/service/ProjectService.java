package com.page.server.service;

import com.page.server.constant.AccessRight;
import com.page.server.dao.PermissionDao;
import com.page.server.dao.ProjectDao;
import com.page.server.dto.ProjectDto;
import com.page.server.entity.*;
import com.page.server.repository.ProjectRepository;
import com.page.server.support.ProjectConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return projectRepository.findAllReadable();
    }

    public ProjectDto.Response toResponse(ProjectDao projectDao, Boolean writable) {
        return ProjectDto.Response.builder()
                .projectNo(projectDao.getProjectNo())
                .projectName(projectDao.getProjectName())
                .managerName(projectDao.getManagerName())
                .createdAt(projectDao.getCreatedAt())
                .ticketCount(projectDao.getTicketCount())
                .writable(writable)
                .build();
    }

    public List<ProjectDto.Response> getProjectListByUser(User user) {
       List<ProjectDto.Response> dtoList = new ArrayList<>();
        if (user.isAdmin()) {
            List<ProjectDao> daoList = projectRepository.findProjectDaoAll();

            daoList.forEach(
                    projectDao -> dtoList.add(
                            toResponse(projectDao, Boolean.TRUE)
                    )
            );
        } else {
            List<PermissionDao> pDaoList = permissionService.getPermissionDaoListByUserNo(user.userNo);

            Map<Long, AccessRight> accessRightMap = pDaoList.stream()
                    .collect(Collectors.toMap(PermissionDao::getContentNo, PermissionDao::getAccessRight));

            List<ProjectDao> tDaoList = projectRepository.findAllByProjectNoContains(
                    accessRightMap.keySet()
            );

            tDaoList.forEach(projectDao -> {
                AccessRight accessRight = accessRightMap.get(projectDao.getProjectNo());

                dtoList.add(
                        toResponse(
                                projectDao,
                                accessRight != null && accessRight.equals(AccessRight.WRITE)
                        )
                );
            });
        }

        return dtoList;
    }


    public ProjectDto.Detail getProjectByUser(User user, Long projectNo) {
        Project project = projectRepository.findById(projectNo).orElse(null);
        if (project == null) {
            return null;
        }

        if (!user.isAdmin() && !project.isReadable(user.userNo, user.groupNo)) {
            return null;
        }

        return projectConvert.to(
                project,
                user.isAdmin() || project.isWritable(user.userNo, user.groupNo)
        );
    }

    @Transactional
    public ProjectDto.Detail createProject(User user, ProjectDto.Request request) {
        List<Permission> permissions = permissionService.addListIfNotExist(request.permissions);
        List<Type> types = typeService.addListIfNotExist(request.types);

        Project project = Project.builder()
                .managerNo(user.userNo)
                .contentName(request.projectName)
                .permissions(permissions)
                .types(types)
                .build();

        return projectConvert.to(
                projectRepository.save(project), Boolean.TRUE
        );
    };

    public void updateProject(User user, ProjectDto.Request request) {
        Project project = projectRepository.findById(request.projectNo).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Not found project.");
        }

        if(!user.isAdmin() && !project.isWritable(user.userNo, user.groupNo)) {
            throw new RuntimeException("You don't have permission.");
        }

        project.contentName = request.projectName;
        project.permissions = permissionService
                .addListIfNotExist(request.permissions);

        projectRepository.save(project);
    };

    public void updateProjectStatus(User user, ProjectDto request) {
        Project project = projectRepository.findById(request.projectNo).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Not found project.");
        }

        if(!user.isAdmin() && !project.isWritable(user.userNo, user.groupNo)) {
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

        if(!user.isAdmin() && !project.isWritable(user.userNo, user.groupNo)) {
            throw new RuntimeException("Not found project.");
        }

        project.deleted = true;

        projectRepository.save(project);
    }

    public void updatePermissions(User user, Long projectNo, List<Permission> permissions) {
        Project project = projectRepository.findById(projectNo).orElse(null);
        if (project == null) {
            throw new IllegalArgumentException("Not found project.");
        }

        if(!user.isAdmin() && !project.isWritable(user.userNo, user.groupNo)) {
            throw new RuntimeException("You don't have permission.");
        }

        project.permissions = permissionService.addListIfNotExist(permissions);

        projectRepository.save(project);
    }
}
