package com.terraformgui.backend.repository;

import com.terraformgui.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository  extends JpaRepository<Project, UUID> {

    Optional<Project> findByProjectCode(String projectCode);
    boolean existsByProjectCode(String projectCode);
}
