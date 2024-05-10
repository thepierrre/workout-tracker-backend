package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;

import java.util.List;
import java.util.UUID;

public interface WorkoutService {


    List<WorkoutDto> findAll();
    WorkoutDto createWorkout(WorkoutDto workoutEntity);

    void deleteById(UUID id);

    void deleteAll();

}
