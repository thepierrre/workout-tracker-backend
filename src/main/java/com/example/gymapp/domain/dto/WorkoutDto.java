package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDto {

    private UUID id;

    @CreationTimestamp
    @NotNull(message = "Timestamp cannot be null.")
    private String timestamp;

    private List<ExerciseInstanceDto> exerciseInstances;

    @NotBlank(message = "User cannot be blank.")
    private UserDto user;


    private TrainingRoutineDto trainingRoutine;

}

// exercise instance cool-exercise e4f3dfd7-6d50-4e66-aab5-bb0c97856956