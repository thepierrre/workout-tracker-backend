package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;

import java.util.ArrayList;
import java.util.UUID;

public class ExerciseTypeDataHelper {

   public static ExerciseTypeEntity createExerciseTypeEntity(
           String name,
           Boolean isDefault
           ) {

        UUID id = UUID.randomUUID();
        return ExerciseTypeEntity.builder()
                .id(id)
                .name(name)
                .isDefault(isDefault)
                .user(null)
                .categories(new ArrayList<>())
                .build();
   }

   public static ExerciseTypeDto createExerciseTypeRequestDto(String name) {
        return ExerciseTypeDto.builder()
                .name(name)
                .categories(new ArrayList<>())
                .build();

   }

   public static ExerciseTypeDto createExerciseTypeResponseDto(
           String name,
           Boolean isDefault
          ) {

        UUID id = UUID.randomUUID();
        return ExerciseTypeDto.builder()
                .id(id)
                .name(name)
                .isDefault(isDefault)
                .user(null)
                .categories(new ArrayList<>())
                .build();

   }


}
