package com.page.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secured/projects")
public class ProjectController {
//    private final ProjectService projectService;
//
//    @GetMapping("")
//    public ResponseEntity<List<ProjectDto.Response>> getProjects(@AuthenticationPrincipal User user, @RequestParam(required = false) Project.Status status) {
//        return ResponseEntity.ok(
//                projectService.getProjectListByUser(user, status)
//        );
//    }
//
//    @PostMapping("")
//    public ResponseEntity<ProjectDto.Response> createProject(@AuthenticationPrincipal User user, @RequestBody ProjectDto.Request request) {
//        return ResponseEntity.ok(
//                projectService.createProject(user, request)
//        );
//    }
//
//    @GetMapping("/{ProjectNo}")
//    public ResponseEntity<ProjectDto.Response> getProject(@AuthenticationPrincipal User user, @PathVariable Long ProjectNo) {
//        return ResponseEntity.ok(
//                projectService.getProjectByUser(user, ProjectNo)
//        );
//    }
//
//    @PutMapping("/status")
//    public ResponseEntity<Object> updateProjectStatus(@AuthenticationPrincipal User user, @RequestBody ProjectDto.Request request) {
//        projectService.updateProjectStatus(user, request);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PutMapping("")
//    public ResponseEntity<Object> updateProject(@AuthenticationPrincipal User user, @RequestBody ProjectDto.Request request) {
//        projectService.updateProject(user, request);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{ProjectNo}")
//    public ResponseEntity<Object> deleteProject(@AuthenticationPrincipal User user, @PathVariable Long ProjectNo) {
//        projectService.deleteProject(user, ProjectNo);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
