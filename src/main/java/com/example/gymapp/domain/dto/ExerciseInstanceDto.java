package com.example.gymapp.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseInstanceDto {

    UUID id;

    @NotNull(message = "You must specify an exercise type for this exercise instance.")
    ExerciseTypeDto exerciseType;

//    SetDto set;
}
