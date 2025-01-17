package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private UUID id;

    @NotBlank(message = "Category name cannot be empty.")
    private String name;

    private String muscleGroup;

    @JsonIgnore
    private List<ExerciseTypeDto> exerciseTypes = new ArrayList<>();

}
