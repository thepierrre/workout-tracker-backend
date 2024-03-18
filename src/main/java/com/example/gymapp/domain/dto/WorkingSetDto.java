package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingSetDto {

    private Short id;

    @NotNull(message = "You must specify a training instance for this set.")
    private ExerciseInstanceDto exerciseInstance;

    private Short reps;

    private Short weight;

}
