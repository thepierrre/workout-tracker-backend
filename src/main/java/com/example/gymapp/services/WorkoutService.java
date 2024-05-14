package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutService {


    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    WorkoutMapper workoutMapper;

    public List<WorkoutDto> findAll() {
        return workoutRepository.findAll().stream().map(workoutMapper::mapToDto).toList();
    }

    public Optional<WorkoutEntity> findById(UUID id) {
        return workoutRepository.findById(id);
    }

    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        WorkoutEntity createdWorkout = workoutRepository.save(workoutMapper.mapFromDto(workoutDto));
        return workoutMapper.mapToDto(createdWorkout);
    }

    public void deleteById(UUID id) {
        workoutRepository.deleteById(id);
    }

    public void deleteAll() {
        workoutRepository.deleteAll();
    }
}
