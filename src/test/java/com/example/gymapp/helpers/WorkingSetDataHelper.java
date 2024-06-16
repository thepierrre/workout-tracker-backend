package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.WorkingSetEntity;

import java.util.Random;
import java.util.UUID;

public class WorkingSetDataHelper {

    public static WorkingSetEntity createWorkingSetEntity(
            Short reps,
            Short weight
    ) {

        Long id = new Random().nextLong();

        return WorkingSetEntity.builder()
                .id(id)
                .reps(reps)
                .weight(weight)
                .build();

    };

    public static WorkingSetDto createWorkingSetResponseDto(
            Short reps,
            Short weight
    ) {
        Long id = new Random().nextLong();

        return WorkingSetDto.builder()
                .id(id)
                .reps(reps)
                .weight(weight)
                .build();
    };

    public static WorkingSetDto createWorkingSetRequestDto(
            Short reps,
            Short weight
    ) {

        return WorkingSetDto.builder()
                .reps(reps)
                .weight(weight)
                .build();
    };

}
