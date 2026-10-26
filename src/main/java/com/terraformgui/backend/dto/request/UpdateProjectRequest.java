package com.terraformgui.backend.dto.request;

import com.terraformgui.backend.enums.CloudProvider;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectRequest {

    @Size(max = 150, message = "Project name cannot exceed 150 characters")
    private String projectName;

    @Size(max = 50, message = "Project code cannot exceed 50 characters")
    private String projectCode;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private CloudProvider cloudProvider;
}