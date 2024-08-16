package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.RoutineEntity;

import java.util.ArrayList;
import java.util.UUID;

public class RoutineDataHelper {


    public static RoutineEntity createRoutineEntity(String name) {

        UUID id = UUID.randomUUID();
        return RoutineEntity.builder()
                .id(id)
                .name(name)
                .user(null)
                .routineExercises(new ArrayList<>())
                .build();
    }

    public static RoutineDto createRoutineRequestDto (String name) {

        return RoutineDto.builder()
                .name(name)
                .routineExercises(new ArrayList<>())
                .build();
    }

    public static RoutineDto createRoutineResponseDto (String name) {
        UUID id = UUID.randomUUID();
        return  RoutineDto.builder()
                .id(id)
                .name(name)
                .user(null)
                .routineExercises(new ArrayList<>())
                .build();
    }
}
