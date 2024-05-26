package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutService {


    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    WorkoutMapper workoutMapper;

    @Autowired
    UserRepository userRepository;

    public List<WorkoutDto> findAll() {
        return workoutRepository.findAll().stream().map(workoutMapper::mapToDto).toList();
    }

    public Optional<WorkoutEntity> findById(UUID id) {
        return workoutRepository.findById(id);
    }

    public WorkoutDto createWorkout(WorkoutDto workoutDto, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        WorkoutEntity workoutEntity = workoutMapper.mapFromDto(workoutDto);
        workoutEntity.setUser(user);

        WorkoutEntity savedWorkoutEntity = workoutRepository.save(workoutEntity);

        return workoutMapper.mapToDto(savedWorkoutEntity);
    }

    public void deleteById(UUID id) {
        if (!workoutRepository.existsById(id)) {
            throw new IllegalArgumentException("Workout not found");
        }

        try {
            workoutRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete workout");
        }

        if (workoutRepository.existsById(id)) {
            throw new RuntimeException("Workout was not deleted");
        }
    }

    public void deleteAll() {
        workoutRepository.deleteAll();
    }
}
