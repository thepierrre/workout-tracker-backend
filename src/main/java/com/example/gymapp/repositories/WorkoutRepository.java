package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {
    Optional<List<WorkoutEntity>> findByUserUsername(String username);
}
