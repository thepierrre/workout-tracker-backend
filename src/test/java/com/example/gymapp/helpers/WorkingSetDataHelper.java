package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.WorkingSetEntity;

import java.util.UUID;

public class WorkingSetDataHelper {

    public static WorkingSetEntity createWorkingSetEntity(
            ExerciseInstanceEntity exercise,
            Short reps,
            Short weight
    ) {

        UUID id = UUID.randomUUID();

        return WorkingSetEntity.builder()
                .id(id)
                .exerciseInstance(exercise)
                .reps(reps)
                .weight(weight)
                .build();

    };

    public static WorkingSetDto createWorkingSetResponseDto(
            ExerciseInstanceDto exercise,
            Short reps,
            Short weight
    ) {
        UUID id = UUID.randomUUID();

        return WorkingSetDto.builder()
                .id(id)
                .exerciseInstance(exercise)
                .reps(reps)
                .weight(weight)
                .build();
    };

    public static WorkingSetDto createWorkingSetRequestDto(
            ExerciseInstanceDto exercise,
            Short reps,
            Short weight
    ) {

        return WorkingSetDto.builder()
                .exerciseInstance(exercise)
                .reps(reps)
                .weight(weight)
                .build();
    };

}
