package com.example.gymapp.services;

import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;

import java.util.List;

public interface ExerciseInstanceService {

    ExerciseInstanceEntity createExerciseInstance(ExerciseInstanceEntity exerciseInstanceEntity);
    List<ExerciseInstanceEntity> findAll();

    void deleteAll();
}
