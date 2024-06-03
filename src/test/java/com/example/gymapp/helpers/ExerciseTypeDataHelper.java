package com.example.gymapp.helpers;

import com.example.gymapp.domain.entities.ExerciseTypeEntity;

import java.util.UUID;

public class ExerciseTypeDataHelper {

    public static ExerciseTypeEntity createExerciseTypeEntity(String name) {
        UUID id = UUID.randomUUID();
        return ExerciseTypeEntity.builder()
                .id(id)
                .name(name)
                .build();
    }
}
