package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.UserSettingsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private UUID id;

    @NotBlank(message = "Username cannot be blank.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @NotBlank(message = "Email cannot be blank.")
    private String email;

    private List<RoutineDto> routines;

    private List<WorkoutDto> workouts;

    private List<ExerciseTypeDto> exerciseTypes;

    @JsonIgnoreProperties({"user"})
    private UserSettingsDto userSettings;

    public UserDto(String id) {
        this.id = UUID.fromString(id);
    }

}
