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
public class RoutineDto {

    public RoutineDto (String name, List<RoutineExerciseDto> exercises) {
        this.name = name;
        this.routineExercises = exercises;
    }

    private UUID id;

    private String name;

    @JsonIgnoreProperties({"routine"})
    private List<RoutineExerciseDto> routineExercises = new ArrayList<>();

    @JsonIgnore
    private UserDto user;

}
