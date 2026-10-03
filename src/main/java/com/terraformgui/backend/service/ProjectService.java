package com.terraformgui.backend.service;

import com.terraformgui.backend.dto.request.CreateProjectRequest;
import com.terraformgui.backend.dto.response.ProjectResponse;
import com.terraformgui.backend.entity.Project;
import com.terraformgui.backend.enums.ProjectStatus;
import com.terraformgui.backend.exception.ConflictException;
import com.terraformgui.backend.exception.ResourceNotFoundException;
import com.terraformgui.backend.mapper.ProjectMapper;
import com.terraformgui.backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
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
}
