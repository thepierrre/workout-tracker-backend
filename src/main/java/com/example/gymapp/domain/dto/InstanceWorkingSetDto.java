package com.example.gymapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstanceWorkingSetDto {

    private UUID id;

    private ExerciseInstanceDto exerciseInstance;

    private Short reps;

    private double weight;

    private LocalDateTime creationTimedate;

    public InstanceWorkingSetDto(String id) {
        this.id = UUID.fromString(id);
    }

}
