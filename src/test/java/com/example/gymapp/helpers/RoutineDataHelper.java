package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoutineDataHelper {


    public static RoutineEntity createRoutineEntity(String name) {

        UUID id = UUID.randomUUID();
        return RoutineEntity.builder()
                .id(id)
                .name(name)
                .user(null)
                .exerciseTypes(new ArrayList<>())
                .build();
    }

    public static RoutineDto createRoutineRequestDto (String name) {

        return RoutineDto.builder()
                .name(name)
                .exerciseTypes(new ArrayList<>())
                .build();
    }

    public static RoutineDto createRoutineResponseDto (String name) {
        UUID id = UUID.randomUUID();
        return  RoutineDto.builder()
                .id(id)
                .name(name)
                .user(null)
                .exerciseTypes(new ArrayList<>())
                .build();
    }
}
