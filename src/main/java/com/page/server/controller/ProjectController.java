package com.page.server.controller;

import com.page.server.dto.ProjectDto;
import com.page.server.entity.Permission;
import com.page.server.entity.User;
import com.page.server.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("")
    public ResponseEntity<List<ProjectDto.Response>> getProjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                projectService.getProjectListByUser(user)
        );
    }

    @PostMapping("")
    public ResponseEntity<ProjectDto.Detail> createProject(@AuthenticationPrincipal User user, @Valid @RequestBody ProjectDto.Request request) {
        return ResponseEntity.ok(
                projectService.createProject(user, request)
        );
    }

    @GetMapping("/{projectNo}")
    public ResponseEntity<ProjectDto.Detail> getProject(@AuthenticationPrincipal User user, @PathVariable Long projectNo) {
        return ResponseEntity.ok(
                projectService.getProjectByUser(user, projectNo)
        );
    }

//    @PutMapping("/status")
//    public ResponseEntity<Object> updateProjectStatus(@AuthenticationPrincipal User user, @RequestBody ProjectDto.Request request) {
//        projectService.updateProjectStatus(user, request);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PutMapping("")
    public ResponseEntity<Object> updateProject(@AuthenticationPrincipal User user, @RequestBody ProjectDto.Request request) {
        projectService.updateProject(user, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{projectNo}")
    public ResponseEntity<Object> deleteProject(@AuthenticationPrincipal User user, @PathVariable Long projectNo) {
        projectService.deleteProject(user, projectNo);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{projectNo}/permissions")
    public ResponseEntity<Object> updateProjectPermissions(@AuthenticationPrincipal User user, @PathVariable Long projectNo, @RequestBody List<Permission> permissions){
        projectService.updatePermissions(user, projectNo, permissions);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
