package com.terraformgui.backend.repository;

import com.terraformgui.backend.entity.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnvironmentRepository  extends JpaRepository<Environment,UUID> {

    Page<Environment> findByProjectIdAndDeletedFalse(
            UUID projectId,
            Pageable pageable
    );

    Optional<Environment> findByIdAndProjectIdAndDeletedFalse(
            UUID id,
            UUID projectId
    );

    boolean existsByProjectIdAndEnvironmentCode(
            UUID projectId,
            String environmentCode
    );
}
