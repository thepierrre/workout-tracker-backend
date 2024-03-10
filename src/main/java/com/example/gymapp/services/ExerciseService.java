package com.example.gymapp.services;

import com.example.gymapp.domain.entities.ExerciseEntity;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {

    ExerciseEntity createExercise(ExerciseEntity exerciseEntity);
    List<ExerciseEntity> findAll();

    void deleteById(UUID id);

    ExerciseEntity update(UUID id, ExerciseEntity exerciseEntity);

    boolean isExists(UUID id);
}
