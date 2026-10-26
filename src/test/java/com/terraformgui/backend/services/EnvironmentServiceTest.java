package com.terraformgui.backend.services;

import com.terraformgui.backend.dto.request.CreateEnvironmentRequest;
import com.terraformgui.backend.dto.response.EnvironmentResponse;
import com.terraformgui.backend.entity.Environment;
import com.terraformgui.backend.entity.Project;
import com.terraformgui.backend.enums.EnvironmentStatus;
import com.terraformgui.backend.exception.ResourceNotFoundException;
import com.terraformgui.backend.mapper.EnvironmentMapper;
import com.terraformgui.backend.repository.EnvironmentRepository;
import com.terraformgui.backend.repository.ProjectRepository;
import com.terraformgui.backend.service.EnvironmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnvironmentServiceTest {

    @Mock
    private EnvironmentRepository environmentRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EnvironmentMapper environmentMapper;

    @InjectMocks
    private EnvironmentService environmentService;

    @Test
    void createEnvironment_shouldCreateEnvironmentSuccessfully() {

        // Arrange: Prepare the test data
        UUID projectId = UUID.randomUUID();

        Project project = new Project();

        CreateEnvironmentRequest request = new CreateEnvironmentRequest();
        request.setEnvironmentName("Development");
        request.setEnvironmentCode("DEV");
        request.setDescription("Development environment");

        Environment environment = new Environment();

        EnvironmentResponse expectedResponse = new EnvironmentResponse();

        // Arrange: Define how mocks should behave
        when(projectRepository.findById(projectId))
                .thenReturn(Optional.of(project));

        when(environmentRepository
                .existsByProjectIdAndEnvironmentCode(projectId, "DEV"))
                .thenReturn(false);

        when(environmentMapper.toEntity(request))
                .thenReturn(environment);

        when(environmentRepository.save(environment))
                .thenReturn(environment);

        when(environmentMapper.toResponse(environment))
                .thenReturn(expectedResponse);

        // Act: Execute the real service method
        EnvironmentResponse actualResponse =
                environmentService.createEnvironment(projectId, request);

        // Assert: Verify the outcome
        assertSame(expectedResponse, actualResponse);

        assertSame(project, environment.getProject());

        assertEquals(EnvironmentStatus.ACTIVE, environment.getStatus());

        verify(environmentRepository).save(environment);
    }

    @Test
    void createEnvironment_shouldThrowExceptionWhenProjectNotFound() {

        // Arrange
        UUID projectId = UUID.randomUUID();

        CreateEnvironmentRequest request = new CreateEnvironmentRequest();
        request.setEnvironmentName("Development");
        request.setEnvironmentCode("DEV");

        when(projectRepository.findById(projectId))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> environmentService.createEnvironment(
                        projectId, request
                )
        );

        // Verify that no environment was saved
        verify(environmentRepository, never()).save(any(Environment.class));
    }
}