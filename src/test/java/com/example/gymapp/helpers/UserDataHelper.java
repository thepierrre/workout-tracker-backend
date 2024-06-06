package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDataHelper {

    public static UserEntity createUserEntity(String username, String email, String password) {

        UUID id = UUID.randomUUID();
        return UserEntity.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .exerciseTypes(new ArrayList<>())
                .build();
    }

    public static UserEntity createUserEntity(
            String username,
            String email,
            String password,
            List<ExerciseTypeEntity> exercises,
            List<RoutineEntity> routines,
            List<WorkoutEntity> workouts
    ) {
        UUID id = UUID.randomUUID();
        return UserEntity.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .exerciseTypes(exercises)
                .routines(routines)
                .workouts(workouts)
                .build();
    }

}
