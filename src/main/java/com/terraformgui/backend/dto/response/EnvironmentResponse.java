package com.terraformgui.backend.dto.response;

import com.terraformgui.backend.enums.EnvironmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter

public class EnvironmentResponse {
    private UUID id;
    private UUID projectId;
    private String environmentName;
    private String environmentCode;
    private String description;
    private EnvironmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
