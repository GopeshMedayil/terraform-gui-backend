package com.terraformgui.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEnvironmentRequest {

    @NotBlank(message = "Environment name is required")
    @Size(max = 100, message = "Environment name cannot exceed 100 characters")
    private String environmentName;

    @NotBlank(message = "Environment code is required")
    @Size(max = 30, message = "Environment code cannot exceed 30 characters")
    private String environmentCode;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
}
