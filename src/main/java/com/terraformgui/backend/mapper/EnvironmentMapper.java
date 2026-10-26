package com.terraformgui.backend.mapper;

import com.terraformgui.backend.dto.request.CreateEnvironmentRequest;
import com.terraformgui.backend.dto.response.EnvironmentResponse;
import com.terraformgui.backend.entity.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentMapper {
    public Environment toEntity(CreateEnvironmentRequest request) {
        Environment environment = new Environment();

        environment.setEnvironmentName(request.getEnvironmentName());
        environment.setEnvironmentCode(request.getEnvironmentCode());
        environment.setDescription(request.getDescription());

        return environment;
    }

    public EnvironmentResponse toResponse(Environment environment) {
        EnvironmentResponse response = new EnvironmentResponse();

        response.setId(environment.getId());
        response.setProjectId(environment.getProject().getId());
        response.setEnvironmentName(environment.getEnvironmentName());
        response.setEnvironmentCode(environment.getEnvironmentCode());
        response.setDescription(environment.getDescription());
        response.setStatus(environment.getStatus());
        response.setCreatedAt(environment.getCreatedAt());
        response.setUpdatedAt(environment.getUpdatedAt());

        return response;
    }
}
