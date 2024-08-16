package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseInstanceDto {

    private UUID id;

    @JsonIgnoreProperties("exerciseInstance")
    private List<InstanceWorkingSetDto> workingSets  = new ArrayList<>();

    private WorkoutDto workout;

    private String exerciseTypeName;

    @Size(max = 300, message="Notes can have a maximum of 300 characters.")
    private String notes;
}
