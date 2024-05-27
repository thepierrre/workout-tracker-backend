package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class RoutineDto {

    private UUID id;

    private String name;

    private List<ExerciseTypeDto> exerciseTypes;

    @JsonIgnoreProperties({"trainingRoutines", "password", "email", "workouts"})
    private UserDto user;

//    private List<WorkoutDto> workouts;

}
