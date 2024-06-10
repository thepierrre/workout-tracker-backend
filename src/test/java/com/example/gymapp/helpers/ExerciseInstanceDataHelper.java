package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;

import java.util.UUID;

public class ExerciseInstanceDataHelper {

    public static ExerciseInstanceEntity createExerciseInstanceEntity(
            String exercise
    ) {
        UUID id = UUID.randomUUID();
        return ExerciseInstanceEntity.builder()
                .id(id)
                .exerciseTypeName(exercise)
                .workout(null)
                .workingSets(null)
                .build();
    }

    public static ExerciseInstanceDto createExerciseInstanceResponseDto(
            String exercise
    ) {
        UUID id = UUID.randomUUID();
        return ExerciseInstanceDto.builder()
                .id(id)
                .exerciseTypeName(exercise)
                .workout(null)
                .workingSets(null)
                .build();
    }

    public static ExerciseInstanceDto createExerciseInstanceRequestDto(
            String exercise
    ) {
        return ExerciseInstanceDto.builder()
                .exerciseTypeName(exercise)
                .workout(null)
                .workingSets(null)
                .build();
    }
}
