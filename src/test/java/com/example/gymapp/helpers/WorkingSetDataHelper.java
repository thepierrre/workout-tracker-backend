package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.InstanceWorkingSetDto;
import com.example.gymapp.domain.entities.InstanceWorkingSetEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class WorkingSetDataHelper {

    public static InstanceWorkingSetEntity createWorkingSetEntity(
            Short reps,
            Short weight,
            LocalDateTime creationTimedate
    ) {

        UUID id = UUID.randomUUID();

        return InstanceWorkingSetEntity.builder()
                .id(id)
                .reps(reps)
                .weight(weight)
                .build();

    };

    public static InstanceWorkingSetDto createWorkingSetResponseDto(
            Short reps,
            Short weight,
            LocalDateTime creationTimedate
    ) {
        UUID id = UUID.randomUUID();

        return InstanceWorkingSetDto.builder()
                .id(id)
                .reps(reps)
                .weight(weight)
                .build();
    };

    public static InstanceWorkingSetDto createWorkingSetRequestDto(
            Short reps,
            Short weight
    ) {

        return InstanceWorkingSetDto.builder()
                .reps(reps)
                .weight(weight)
                .build();
    };

    public static void main(String[] args) {
        InstanceWorkingSetEntity instanceWorkingSetEntity1 = WorkingSetDataHelper.createWorkingSetEntity((short) 10, (short) 50, LocalDateTime.now());

    }

}
