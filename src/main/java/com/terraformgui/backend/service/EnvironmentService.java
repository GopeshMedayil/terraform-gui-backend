package com.terraformgui.backend.service;

import com.terraformgui.backend.dto.request.CreateEnvironmentRequest;
import com.terraformgui.backend.dto.request.UpdateEnvironmentRequest;
import com.terraformgui.backend.dto.response.EnvironmentResponse;
import com.terraformgui.backend.entity.Environment;
import com.terraformgui.backend.entity.Project;
import com.terraformgui.backend.enums.EnvironmentStatus;
import com.terraformgui.backend.exception.ConflictException;
import com.terraformgui.backend.exception.ResourceNotFoundException;
import com.terraformgui.backend.mapper.EnvironmentMapper;
import com.terraformgui.backend.repository.EnvironmentRepository;
import com.terraformgui.backend.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnvironmentService {

    private final EnvironmentRepository environmentRepository;
    private final ProjectRepository projectRepository;
    private final EnvironmentMapper environmentMapper;

    public EnvironmentResponse createEnvironment(
            UUID projectId,
            CreateEnvironmentRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found for id: " + projectId
                        )
                );

        if (environmentRepository.existsByProjectIdAndEnvironmentCode(
                projectId, request.getEnvironmentCode())) {

            throw new ConflictException(
                    "Environment code already exists in this project"
            );
        }

        Environment environment = environmentMapper.toEntity(request);

        environment.setProject(project);
        environment.setStatus(EnvironmentStatus.ACTIVE);

        Environment savedEnvironment =
                environmentRepository.save(environment);

        return environmentMapper.toResponse(savedEnvironment);
    }

    public Page<EnvironmentResponse> getEnvironments(
            UUID projectId,
            Pageable pageable) {

        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException(
                    "Project not found for id: " + projectId
            );
        }

        Page<Environment> environments =
                environmentRepository.findByProjectIdAndDeletedFalse(
                        projectId, pageable
                );

        return environments.map(environmentMapper::toResponse);
    }

    public EnvironmentResponse getEnvironment(
            UUID projectId,
            UUID environmentId) {

        Environment environment =
                environmentRepository
                        .findByIdAndProjectIdAndDeletedFalse(
                                environmentId,
                                projectId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Environment not found for id: "
                                                + environmentId
                                )
                        );

        return environmentMapper.toResponse(environment);
    }

    @Transactional
    public EnvironmentResponse updateEnvironment(
            UUID projectId,
            UUID environmentId,
            UpdateEnvironmentRequest request) {

        Environment environment =
                environmentRepository
                        .findByIdAndProjectIdAndDeletedFalse(
                                environmentId,
                                projectId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Environment not found for id: "
                                                + environmentId
                                )
                        );

        if (request.getEnvironmentCode() != null
                && !request.getEnvironmentCode()
                .equals(environment.getEnvironmentCode())
                && environmentRepository
                .existsByProjectIdAndEnvironmentCode(
                        projectId,
                        request.getEnvironmentCode()
                )) {

            throw new ConflictException(
                    "Environment code already exists in this project"
            );
        }

        if (request.getEnvironmentName() != null) {
            environment.setEnvironmentName(
                    request.getEnvironmentName()
            );
        }

        if (request.getEnvironmentCode() != null) {
            environment.setEnvironmentCode(
                    request.getEnvironmentCode()
            );
        }

        if (request.getDescription() != null) {
            environment.setDescription(
                    request.getDescription()
            );
        }

        if (request.getStatus() != null) {
            environment.setStatus(request.getStatus());
        }

        return environmentMapper.toResponse(environment);
    }

    @Transactional
    public void deleteEnvironment(UUID projectId, UUID environmentId) {

        Environment environment =
                environmentRepository
                        .findByIdAndProjectIdAndDeletedFalse(
                                environmentId,
                                projectId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Environment not found for id: "
                                                + environmentId
                                )
                        );

        environment.setDeleted(true);
    }
}