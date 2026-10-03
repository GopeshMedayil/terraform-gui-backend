package com.terraformgui.backend.dto.response;

import com.terraformgui.backend.enums.CloudProvider;
import com.terraformgui.backend.enums.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ProjectResponse {
    private UUID id;

    private String projectName;

    private String projectCode;

    private String description;

    private CloudProvider cloudProvider;

    private ProjectStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
