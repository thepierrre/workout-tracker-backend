package com.example.gymapp.services.impl;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.WorkoutRepository;
import com.example.gymapp.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    WorkoutMapper workoutMapper;

    @Override
    public List<WorkoutDto> findAll() {
        return workoutRepository.findAll().stream().map(workoutMapper::mapToDto).toList();
    }

    @Override
    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        WorkoutEntity createdWorkout = workoutRepository.save(workoutMapper.mapFromDto(workoutDto));
        return workoutMapper.mapToDto(createdWorkout);
    }

    @Override
    public void deleteById(UUID id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        workoutRepository.deleteAll();
    }

}
