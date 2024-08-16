package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.UserSettingsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
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

    private List<RoutineDto> routines = new ArrayList<>();

    private List<WorkoutDto> workouts = new ArrayList<>();

    private List<ExerciseTypeDto> exerciseTypes = new ArrayList<>();

    @JsonIgnoreProperties({"user"})
    private UserSettingsDto userSettings;

    public UserDto(String id) {
        this.id = UUID.fromString(id);
    }

}
