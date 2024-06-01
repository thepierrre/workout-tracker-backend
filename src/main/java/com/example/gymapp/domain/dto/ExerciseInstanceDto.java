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
public class ExerciseInstanceDto {

    private UUID id;

//    @NotNull(message = "Exercise type cannot be null.")
//    private ExerciseTypeDto exerciseType;

    private String exerciseTypeName;

    @JsonIgnoreProperties("exerciseInstance")
    private List<WorkingSetDto> workingSets;

    private WorkoutDto workout;
}
