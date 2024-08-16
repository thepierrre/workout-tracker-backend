package com.example.gymapp.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstanceWorkingSetDto {

    private UUID id;

    private ExerciseInstanceDto exerciseInstance;

    private Short reps;

    private Double weight;

    private LocalDateTime creationTimedate;

    public InstanceWorkingSetDto(String id) {
        this.id = UUID.fromString(id);
    }

}
