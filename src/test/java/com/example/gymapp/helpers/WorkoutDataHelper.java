package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkoutDataHelper {

    public static WorkoutEntity createWorkoutEntity(
            LocalDate creationDate,
            String routineName
    ) {

        UUID id = UUID.randomUUID();
        return WorkoutEntity.builder()
                .id(id)
                .creationDate(creationDate)
                .exerciseInstances(new ArrayList<>())
                .user(null)
                .routineName(routineName)
                .build();
    };

    public static WorkoutDto createWorkoutRequestDto(
            LocalDate creationDate,
            String routineName
    ) {
        return WorkoutDto.builder()
                .creationDate(creationDate)
                .routineName(routineName)
                .build();
    };

    public static WorkoutDto createWorkoutResponseDto(
            LocalDate creationDate,
            String routineName
    ) {
        UUID id = UUID.randomUUID();
        return WorkoutDto.builder()
                .id(id)
                .creationDate(creationDate)
                .exerciseInstances(new ArrayList<>())
                .user(null)
                .routineName(routineName)
                .build();
    };


}
