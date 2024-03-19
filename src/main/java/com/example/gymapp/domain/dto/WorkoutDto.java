package com.example.gymapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDto {

    private UUID id;

    private LocalDateTime timestamp;

    private List<ExerciseInstanceDto> exerciseInstances;

    private UserDto user;

}
