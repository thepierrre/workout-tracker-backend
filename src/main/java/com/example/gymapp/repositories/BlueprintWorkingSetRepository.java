package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.BlueprintWorkingSetEntity;
import com.example.gymapp.domain.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BlueprintWorkingSetRepository extends JpaRepository<BlueprintWorkingSetEntity, UUID> {
    boolean existsById(UUID id);
}
