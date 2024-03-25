package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;

import java.util.List;
import java.util.UUID;

public interface ExerciseInstanceService {

    ExerciseInstanceDto createExerciseInstance(ExerciseInstanceDto exerciseInstanceDto);
    List<ExerciseInstanceDto> findAll();

    void deleteAll();

    void deleteById(UUID id);
}
