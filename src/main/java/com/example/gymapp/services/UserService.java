package com.example.gymapp.services;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.TrainingRoutineDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> findAll();

    void deleteById(UUID id);

    void deleteAll();
    UserDto update(UUID id, UserDto userDto);
    boolean isExists(UUID id);

    List<TrainingRoutineDto> getTrainingRoutinesForUser(UUID id);

    List<ExerciseTypeDto> getExerciseTypesForUser(UUID id);

    List<WorkoutDto> getWorkoutsForUser(UUID id);


}
