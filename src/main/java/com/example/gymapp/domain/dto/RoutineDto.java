package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class RoutineDto {

    private UUID id;

    private String name;

    @JsonIgnoreProperties({"routine", "exerciseType"})
    private List<RoutineExerciseDto> routineExercises = new ArrayList<>();

    @JsonIgnore
    private UserDto user;

}
