package com.example.gymapp.services;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {

    ExerciseTypeEntity createExercise(ExerciseTypeEntity exerciseTypeEntity);

    List<ExerciseTypeEntity> findAll();

    void deleteById(Long id);

    void deleteAll();

    ExerciseTypeEntity update(Long id, ExerciseTypeEntity exerciseTypeEntity);

    boolean isExists(Long id);
}
