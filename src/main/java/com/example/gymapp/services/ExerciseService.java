package com.example.gymapp.services;

import com.example.gymapp.domain.entities.ExerciseEntity;

import java.util.List;

public interface ExerciseService {

    ExerciseEntity createExercise(ExerciseEntity exerciseEntity);
    List<ExerciseEntity> findAll();

    void deleteById(Long id);

}
