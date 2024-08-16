package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineExerciseDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineExerciseEntity;

import java.util.ArrayList;
import java.util.UUID;

public class RoutineExerciseDataHelper {

    public static RoutineExerciseEntity createRoutineExerciseEntity(
            String name
    ) {

        UUID id = UUID.randomUUID();
        return RoutineExerciseEntity.builder()
                .id(id)
                .name(name)
                .workingSets(new ArrayList<>())
                .build();
    }

    public static RoutineExerciseDto createRoutineExerciseRequestDto(String name) {
        return RoutineExerciseDto.builder()
                .name(name)
                .workingSets(new ArrayList<>())
                .build();

    }

    public static RoutineExerciseDto createRoutineExerciseResponseDto(
            String name
    ) {

        UUID id = UUID.randomUUID();
        return RoutineExerciseDto.builder()
                .id(id)
                .name(name)
                .workingSets(new ArrayList<>())
                .build();

    }


}

