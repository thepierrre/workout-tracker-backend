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
public class ExerciseInstanceDto {

    private UUID id;

    private String exerciseTypeName;

    @JsonIgnoreProperties("exerciseInstance")
    private List<InstanceWorkingSetDto> workingSets;

    private WorkoutDto workout;
}
