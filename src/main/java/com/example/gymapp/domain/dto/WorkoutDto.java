package com.example.gymapp.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private List<ExerciseInstanceDto> exerciseInstances;

    @JsonIgnoreProperties({"routines", "password", "email", "workouts"})
    private UserDto user;

    private String routineName;

    public WorkoutDto(String id) {
        this.id = UUID.fromString(id);
    }

}
