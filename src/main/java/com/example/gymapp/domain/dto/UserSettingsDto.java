package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettingsDto {

    private UUID id;

    @JsonIgnoreProperties({ "username", "password", "email", "routines", "workouts", "exerciseTypes", "userSettings"})
    private UserDto user;

    private short changeThreshold;
}
