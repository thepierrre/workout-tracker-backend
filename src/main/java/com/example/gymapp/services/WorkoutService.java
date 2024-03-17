package com.example.gymapp.services;

import com.example.gymapp.domain.entities.WorkoutEntity;

import java.util.List;
import java.util.UUID;

public interface WorkoutService {


    List<WorkoutEntity> findAll();
    WorkoutEntity createWorkout(WorkoutEntity workoutEntity);

    void deleteById(UUID id);

    void deleteAll();

}
