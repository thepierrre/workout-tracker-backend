package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutineExerciseDto {

    private UUID id;

    private String name;

    @JsonIgnore
    private RoutineDto routine;

    @JsonIgnoreProperties("routineExercise")
    private List<BlueprintWorkingSetDto> workingSets = new ArrayList<>();
}
