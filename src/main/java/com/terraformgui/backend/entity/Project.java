package com.terraformgui.backend.entity;

import com.terraformgui.backend.enums.CloudProvider;
import com.terraformgui.backend.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "projects")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name="project_name",nullable = false,length = 150)
    private String projectName;

    @Column(name="project_code",nullable = true,length = 50)
    private String projectCode;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "cloud_provider", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private CloudProvider cloudProvider;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean deleted = false;
}
