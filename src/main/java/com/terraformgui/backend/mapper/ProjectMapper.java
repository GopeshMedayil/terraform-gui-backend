package com.terraformgui.backend.mapper;

import com.terraformgui.backend.dto.request.CreateProjectRequest;
import com.terraformgui.backend.dto.response.ProjectResponse;
import com.terraformgui.backend.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(CreateProjectRequest request) {
        Project project = new Project();

        project.setProjectName(request.getProjectName());
        project.setProjectCode(request.getProjectCode());
        project.setDescription(request.getDescription());
        project.setCloudProvider(request.getCloudProvider());

        return project;
    }

    public ProjectResponse toResponse(Project project) {
        ProjectResponse response = new ProjectResponse();

        response.setId(project.getId());
        response.setProjectName(project.getProjectName());
        response.setProjectCode(project.getProjectCode());
        response.setDescription(project.getDescription());
        response.setCloudProvider(project.getCloudProvider());
        response.setStatus(project.getStatus());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());

        return response;
    }
}
