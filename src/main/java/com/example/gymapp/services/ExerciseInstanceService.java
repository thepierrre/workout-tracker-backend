package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;

import java.util.List;

public interface ExerciseInstanceService {

    ExerciseInstanceDto createExerciseInstance(ExerciseInstanceDto exerciseInstanceDto);
    List<ExerciseInstanceDto> findAll();

    void deleteAll();
}
