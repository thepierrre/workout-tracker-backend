package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDto {

    private UUID id;

    private LocalDate creationDate;

    @JsonIgnoreProperties({"exerciseType", "workout"})
    private List<ExerciseInstanceDto> exerciseInstances = new ArrayList<>();

    @JsonIgnore
    private UserDto user;

    private String routineName;

    public WorkoutDto(String id) {
        this.id = UUID.fromString(id);
    }

}
