package com.example.gymapp.services;

import com.example.gymapp.domain.entities.ExerciseEntity;

public interface ExerciseService {

    ExerciseEntity createExercise(ExerciseEntity exerciseEntity);

    ExerciseEntity save(ExerciseEntity exerciseEntity);
}
