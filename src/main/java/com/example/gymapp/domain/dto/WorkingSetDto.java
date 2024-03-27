package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingSetDto {

    private UUID id;

    @NotNull(message = "Training instance cannot be null.")
    private ExerciseInstanceDto exerciseInstance;

    private Short reps;

    private Short weight;

}
