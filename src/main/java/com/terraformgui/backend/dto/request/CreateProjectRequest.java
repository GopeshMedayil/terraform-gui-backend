package com.terraformgui.backend.dto.request;

import com.terraformgui.backend.enums.CloudProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectRequest {

    @NotBlank(message = "Project name is required")
    @Size(max = 150, message = "Project name cannot exceed 150 characters")
    private String projectName;

    @NotBlank(message = "Project code is required")
    @Size(max = 50, message = "Project code cannot exceed 50 characters")
    private String projectCode;

    @Size(max = 1000)
    private String description;

    @NotNull(message = "Cloud provider is required")
    private CloudProvider cloudProvider;
}
