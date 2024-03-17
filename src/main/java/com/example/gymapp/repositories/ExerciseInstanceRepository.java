package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseInstanceRepository extends JpaRepository<ExerciseInstanceEntity, UUID> {
}
