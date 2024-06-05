package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;

import java.util.List;
import java.util.UUID;

public class ExerciseTypeDataHelper {

    public static ExerciseTypeEntity createExerciseTypeEntity(String name, UUID id) {
        return ExerciseTypeEntity.builder()
                .id(id)
                .name(name)
                .build();
    }

   public static ExerciseTypeEntity createExerciseTypeEntity(
           String name,
           UserEntity user,
           List<CategoryEntity> categories,
           List<RoutineEntity> routines) {

        UUID id = UUID.randomUUID();
        return ExerciseTypeEntity.builder()
                .id(id)
                .name(name)
                .user(user)
                .categories(categories)
                .routines(routines)
                .build();
   }

   public static ExerciseTypeDto createExerciseTypeRequestDto(String name, List<CategoryDto> categories) {
        return ExerciseTypeDto.builder()
                .name(name)
                .categories(categories)
                .build();

   }

   public static ExerciseTypeDto createExerciseTypeResponseDto(
           String name,
           UserDto user,
           List<CategoryDto> categories,
           List<RoutineDto> routines) {

        UUID id = UUID.randomUUID();
        return ExerciseTypeDto.builder()
                .name(name)
                .user(user)
                .categories(categories)
                .routines(routines)
                .build();

   }


}
