package com.example.gymapp.services.impl;

import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.repositories.WorkoutRepository;
import com.example.gymapp.services.WorkoutService;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Override
    public WorkoutEntity createWorkout(WorkoutEntity workoutEntity) {
        return workoutRepository.save(workoutEntity);
    }
}
