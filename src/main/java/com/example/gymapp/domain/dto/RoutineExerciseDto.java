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
public class RoutineExerciseDto {

    private UUID id;

    private String name;

    private UUID exerciseTypeId;

    @JsonIgnoreProperties({"routineExercises", "user"})
    private RoutineDto routine;

    @JsonIgnoreProperties({"user", "categories", "routineExercises"})
    private ExerciseTypeDto exerciseType;

    @JsonIgnoreProperties("routineExercise")
    private List<BlueprintWorkingSetDto> workingSets;
}
