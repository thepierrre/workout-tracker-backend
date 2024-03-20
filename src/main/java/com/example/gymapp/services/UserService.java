package com.example.gymapp.services;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    List<UserEntity> findAll();

    void deleteById(UUID id);

    void deleteAll();
    UserEntity update(UUID id, UserEntity userEntity);
    boolean isExists(UUID id);

    List<TrainingRoutineEntity> getTrainingRoutinesForUser(UUID id);

    List<ExerciseTypeEntity> getExerciseTypesForUser(UUID id);

    List<WorkoutEntity> getWorkoutsForUser(UUID id);


}
