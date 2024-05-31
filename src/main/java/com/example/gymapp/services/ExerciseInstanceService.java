package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.ExerciseInstanceMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import com.example.gymapp.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExerciseInstanceService {

    @Autowired
    ExerciseInstanceMapper exerciseInstanceMapper;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    public List<ExerciseInstanceDto> findAll() {
        return exerciseInstanceRepository.findAll().stream().map(exerciseInstanceMapper::mapToDto).toList();
    }

    public List<ExerciseInstanceDto> findAllForWorkout(UUID workoutId) {
        WorkoutEntity workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Workout with the ID %s not found.", workoutId.toString())));

        return workout.getExerciseInstances().stream()
                .map(exerciseInstanceEntity -> exerciseInstanceMapper.mapToDto(exerciseInstanceEntity)).toList();
    }

    public ExerciseInstanceDto createExerciseInstance(ExerciseInstanceDto exerciseInstanceDto) {
        ExerciseInstanceEntity exerciseInstanceEntity = exerciseInstanceMapper.mapFromDto(exerciseInstanceDto);
        exerciseInstanceRepository.save(exerciseInstanceEntity);
        return exerciseInstanceMapper.mapToDto(exerciseInstanceEntity);
    }

//    public ExerciseInstanceDto patchById(UUID exerciseInstanceId) {
//        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseInstanceId)
//                .orElseThrow(() -> new EntityNotFoundException(String.format(
//                        "Exercise instance with the ID %s not found.", exerciseInstanceId.toString())));
//
//
//
//    }

    public void deleteAll() {
        exerciseInstanceRepository.deleteAll();
    }

    public void deleteById(UUID exerciseInstanceId) {

        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseInstanceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Exercise instance with the ID %s not found.", exerciseInstanceId.toString())));

        exerciseInstanceRepository.deleteById(exerciseInstanceId);
    }
}
