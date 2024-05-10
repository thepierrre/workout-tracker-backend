package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {
}
