package com.page.server.controller;

import com.page.server.dto.ProjectDto;
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

    @GetMapping("/{ProjectNo}")
    public ResponseEntity<ProjectDto.Detail> getProject(@AuthenticationPrincipal User user, @PathVariable Long ProjectNo) {
        return ResponseEntity.ok(
                projectService.getProjectByUser(user, ProjectNo)
        );
    }

    @PutMapping("/status")
    public ResponseEntity<Object> updateProjectStatus(@AuthenticationPrincipal User user, @RequestBody ProjectDto.Request request) {
        projectService.updateProjectStatus(user, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Object> updateProject(@AuthenticationPrincipal User user, @RequestBody ProjectDto.Request request) {
        projectService.updateProject(user, request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{ProjectNo}")
    public ResponseEntity<Object> deleteProject(@AuthenticationPrincipal User user, @PathVariable Long ProjectNo) {
        projectService.deleteProject(user, ProjectNo);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
