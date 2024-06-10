package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExerciseTypeDataHelper {

   public static ExerciseTypeEntity createExerciseTypeEntity(
           String name
           ) {

        UUID id = UUID.randomUUID();
        return ExerciseTypeEntity.builder()
                .id(id)
                .name(name)
                .user(null)
                .categories(new ArrayList<>())
                .routines(new ArrayList<>())
                .build();
   }

   public static ExerciseTypeDto createExerciseTypeRequestDto(String name) {
        return ExerciseTypeDto.builder()
                .name(name)
                .categories(new ArrayList<>())
                .build();

   }

   public static ExerciseTypeDto createExerciseTypeResponseDto(
           String name
          ) {

        UUID id = UUID.randomUUID();
        return ExerciseTypeDto.builder()
                .id(id)
                .name(name)
                .user(null)
                .categories(new ArrayList<>())
                .routines(new ArrayList<>())
                .build();

   }


}
