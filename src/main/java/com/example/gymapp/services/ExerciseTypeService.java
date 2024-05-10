package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;

import java.util.List;
import java.util.UUID;

public interface ExerciseTypeService {

    ExerciseTypeDto createExercise(ExerciseTypeDto exerciseTypeDto);

    List<ExerciseTypeEntity> findAll();

    void deleteById(UUID id);

    void deleteAll();

    ExerciseTypeDto update(UUID id, ExerciseTypeDto exerciseTypeDto);

    boolean isExists(UUID id);
}
