package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseTypeEntity, Long> {
}
