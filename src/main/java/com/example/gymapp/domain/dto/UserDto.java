package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    @NotBlank(message = "You must specify a username.")
    private String username;

    @NotBlank(message = "You must specify a password.")
    private String password;

    private List<TrainingRoutineDto> trainingRoutines;

    private List<WorkoutDto> workouts;

    private List<ExerciseTypeDto> exerciseTypes;
}
