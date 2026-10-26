package com.terraformgui.backend.controller;

import com.terraformgui.backend.dto.request.CreateEnvironmentRequest;
import com.terraformgui.backend.dto.request.UpdateEnvironmentRequest;
import com.terraformgui.backend.dto.response.EnvironmentResponse;
import com.terraformgui.backend.service.EnvironmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/environments")
@RequiredArgsConstructor
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @PostMapping
    public ResponseEntity<EnvironmentResponse> createEnvironment(
            @PathVariable UUID projectId,
            @Valid @RequestBody CreateEnvironmentRequest request) {

        EnvironmentResponse response =
                environmentService.createEnvironment(projectId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<EnvironmentResponse>> getEnvironments(
            @PathVariable UUID projectId,
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {

        Page<EnvironmentResponse> response =
                environmentService.getEnvironments(projectId, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{environmentId}")
    public ResponseEntity<EnvironmentResponse> getEnvironment(
            @PathVariable UUID projectId,
            @PathVariable UUID environmentId) {

        EnvironmentResponse response =
                environmentService.getEnvironment(projectId, environmentId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{environmentId}")
    public ResponseEntity<EnvironmentResponse> updateEnvironment(
            @PathVariable UUID projectId,
            @PathVariable UUID environmentId,
            @Valid @RequestBody UpdateEnvironmentRequest request) {

        EnvironmentResponse response =
                environmentService.updateEnvironment(
                        projectId, environmentId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{environmentId}")
    public ResponseEntity<Void> deleteEnvironment(
            @PathVariable UUID projectId,
            @PathVariable UUID environmentId) {

        environmentService.deleteEnvironment(projectId, environmentId);

        return ResponseEntity.noContent().build();
    }
}
