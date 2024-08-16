package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.UserSettingsDto;
import com.example.gymapp.domain.entities.InstanceWorkingSetEntity;
import com.example.gymapp.domain.entities.UserSettingsEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class UserSettingsDataHelper {

    public static UserSettingsEntity createUserSettingsEntity(
            Double changeThreshold,
            String weightUnit
    ) {
        UUID id = UUID.randomUUID();

        return UserSettingsEntity.builder()
                .id(id)
                .changeThreshold(changeThreshold)
                .weightUnit(weightUnit)
                .build();
    };

    public static UserSettingsDto createUserSettingsRequestDto(
            Double changeThreshold,
            String weightUnit
    ) {
        return UserSettingsDto.builder()
                .changeThreshold(changeThreshold)
                .weightUnit(weightUnit)
                .build();
    }

    public static UserSettingsDto createUserSettingsRequestDto(
            Double changeThreshold
    ) {
        return UserSettingsDto.builder()
                .changeThreshold(changeThreshold)
                .build();
    }

    public static UserSettingsDto createUserSettingsRequestDto(
            String weightUnit
    ) {
        return UserSettingsDto.builder()
                .weightUnit(weightUnit)
                .build();
    }

    public static UserSettingsDto createUserSettingsResponseDto(
            Double changeThreshold,
            String weightUnit
    ) {
        UUID id = UUID.randomUUID();

        return UserSettingsDto.builder()
                .id(id)
                .changeThreshold(changeThreshold)
                .weightUnit(weightUnit)
                .build();
    }
}
