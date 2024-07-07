package com.example.gymapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private String username;

    private String email;

    private List<RoutineDto> routines;

    private List<WorkoutDto> workouts;

    private List<ExerciseTypeDto> exerciseTypes;

    private short changeThreshold;

}
