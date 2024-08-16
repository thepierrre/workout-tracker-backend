package com.example.gymapp.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private String username;

    private String email;

    private List<RoutineDto> routines;

    private List<WorkoutDto> workouts;

    private List<ExerciseTypeDto> exerciseTypes;

}
