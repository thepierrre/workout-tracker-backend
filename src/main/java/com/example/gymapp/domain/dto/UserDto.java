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

    @NotBlank(message = "The username must not be empty.")
    private String username;

    @NotBlank(message = "The password must not be empty.")
    private String password;

    private List<TrainingRoutineDto> trainingRoutines;
}
