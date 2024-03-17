package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingRoutineDto {

    private Long id;

    private String name;

    private List<ExerciseTypeDto> exercises;

    @NotNull(message = "You must specify a user for this training type.")
    private UserDto user;

}
