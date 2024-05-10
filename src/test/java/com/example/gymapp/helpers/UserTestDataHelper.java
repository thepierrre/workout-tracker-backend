package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.UserEntity;

import java.util.UUID;

public class UserTestDataHelper {

    private UserTestDataHelper() {}


    public static UserDto createUserDto(
            String username,
            String email,
            String password
    ) {
        UUID id = UUID.randomUUID();
        return UserDto.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }

    public static UserEntity createUserEntity(UserDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
