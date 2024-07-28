package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.RoutineExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoutineExerciseRepository extends JpaRepository<RoutineExerciseEntity, UUID> {
}
