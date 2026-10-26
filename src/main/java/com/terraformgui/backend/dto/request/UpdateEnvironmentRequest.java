package com.terraformgui.backend.dto.request;

import com.terraformgui.backend.enums.EnvironmentStatus;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEnvironmentRequest {

    @Size(max = 100, message = "Environment name cannot exceed 100 characters")
    private String environmentName;

    @Size(max = 30, message = "Environment code cannot exceed 30 characters")
    private String environmentCode;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private EnvironmentStatus status;
}
