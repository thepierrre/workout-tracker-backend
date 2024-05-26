package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingRoutineDto {

    private UUID id;

    private String name;

    private List<ExerciseTypeDto> exerciseTypes;

    @JsonIgnoreProperties({"trainingRoutines", "password", "email", "workouts"})
    private UserDto user;

//    private List<WorkoutDto> workouts;

}
