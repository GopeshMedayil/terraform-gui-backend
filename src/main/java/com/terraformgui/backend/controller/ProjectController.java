package com.terraformgui.backend.controller;

import com.terraformgui.backend.dto.request.CreateProjectRequest;
import com.terraformgui.backend.dto.request.UpdateProjectRequest;
import com.terraformgui.backend.dto.response.ProjectResponse;
import com.terraformgui.backend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody CreateProjectRequest request
            ){

        ProjectResponse response = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(
            @PathVariable UUID id) {

        ProjectResponse response = projectService.findProjectByID(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectResponse>> getProjects(@PageableDefault(size = 20,
            sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ProjectResponse> response =
                projectService.findAllProjects(pageable);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProjectRequest request) {

        ProjectResponse response =
                projectService.updateProject(id, request);

        return ResponseEntity.ok(response);
    }
}
