package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.WorkingSetEntity;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class WorkingSetDataHelper {

    public static WorkingSetEntity createWorkingSetEntity(
            Short reps,
            Short weight,
            LocalDateTime creationTimedate
    ) {

        UUID id = UUID.randomUUID();

        return WorkingSetEntity.builder()
                .id(id)
                .reps(reps)
                .weight(weight)
                .build();

    };

    public static WorkingSetDto createWorkingSetResponseDto(
            Short reps,
            Short weight,
            LocalDateTime creationTimedate
    ) {
        UUID id = UUID.randomUUID();

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

    public static void main(String[] args) {
        WorkingSetEntity workingSetEntity1 = WorkingSetDataHelper.createWorkingSetEntity((short) 10, (short) 50, LocalDateTime.now());

    }

}
