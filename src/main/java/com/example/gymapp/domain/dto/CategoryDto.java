package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
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
public class CategoryDto {

    private UUID id;

    @NotBlank(message = "Category name cannot be empty.")
    private String name;

    @JsonIgnoreProperties("categories")
    private List<ExerciseTypeDto> exerciseTypes;

}
