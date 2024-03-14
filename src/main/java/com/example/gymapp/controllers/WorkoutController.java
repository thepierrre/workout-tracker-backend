package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @Autowired
    WorkoutMapper workoutMapper;

    @PostMapping(path = "/workouts")
    public ResponseEntity<WorkoutDto> createWorkout(WorkoutDto workoutDto) {
        WorkoutEntity workoutEntity = workoutMapper.mapFrom(workoutDto);
        WorkoutEntity createdWorkout = workoutService.createWorkout(workoutEntity);
        return new ResponseEntity<>(workoutMapper.mapTo(workoutEntity), HttpStatus.CREATED);
    }
}
