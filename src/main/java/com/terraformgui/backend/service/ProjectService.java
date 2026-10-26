package com.terraformgui.backend.service;

import com.terraformgui.backend.dto.request.CreateProjectRequest;
import com.terraformgui.backend.dto.request.UpdateProjectRequest;
import com.terraformgui.backend.dto.response.ProjectResponse;
import com.terraformgui.backend.entity.Project;
import com.terraformgui.backend.enums.ProjectStatus;
import com.terraformgui.backend.exception.ConflictException;
import com.terraformgui.backend.exception.ResourceNotFoundException;
import com.terraformgui.backend.mapper.ProjectMapper;
import com.terraformgui.backend.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectResponse createProject(CreateProjectRequest request){
        if(projectRepository.existsByProjectCode(request.getProjectCode())){
           throw new ConflictException("Project already exists !");
        }
        Project project = projectMapper.toEntity(request);
        project.setStatus(ProjectStatus.ACTIVE);

        Project savedProject = projectRepository.save(project);

        return projectMapper.toResponse(savedProject);
    }

    public ProjectResponse findProjectByID(UUID id){
        Project project = projectRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Project not found for "+id));
        return projectMapper.toResponse(project);
    }

    public Page<ProjectResponse> findAllProjects(Pageable pageable){
        Page<Project> projects = projectRepository.findByDeletedFalse(pageable);
        return projects.map(projectMapper::toResponse);
    }

    @Transactional
    public ProjectResponse updateProject(
            UUID id,
            UpdateProjectRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found for id: " + id
                        )
                );

        if (request.getProjectCode() != null &&
                !request.getProjectCode().equals(project.getProjectCode()) &&
                projectRepository.existsByProjectCode(request.getProjectCode())) {

            throw new ConflictException("Project code already exists");
        }

        if (request.getProjectName() != null) {
            project.setProjectName(request.getProjectName());
        }

        if (request.getProjectCode() != null) {
            project.setProjectCode(request.getProjectCode());
        }

        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }

        if (request.getCloudProvider() != null) {
            project.setCloudProvider(request.getCloudProvider());
        }

        return projectMapper.toResponse(project);
    }
}
