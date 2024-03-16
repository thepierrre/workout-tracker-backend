package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.ExerciseEntity;
import com.example.gymapp.domain.entities.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingTypeDto {

    private UUID id;

    private String name;

    private List<ExerciseDto> exercises;

    @NotNull(message = "You must specify a user for this training type.")
    private UserDto user;

}
