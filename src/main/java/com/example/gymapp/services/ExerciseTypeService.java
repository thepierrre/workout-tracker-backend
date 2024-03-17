package com.example.gymapp.services;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;

import java.util.List;
import java.util.UUID;

public interface ExerciseTypeService {

    ExerciseTypeEntity createExercise(ExerciseTypeEntity exerciseTypeEntity);

    List<ExerciseTypeEntity> findAll();

    void deleteById(UUID id);

    void deleteAll();

    ExerciseTypeEntity update(UUID id, ExerciseTypeEntity exerciseTypeEntity);

    boolean isExists(UUID id);
}
