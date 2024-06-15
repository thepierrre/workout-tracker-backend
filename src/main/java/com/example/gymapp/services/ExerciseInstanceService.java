package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.entities.ExerciseInstanceEntity;
import com.example.gymapp.domain.entities.WorkingSetEntity;
import com.example.gymapp.domain.entities.WorkoutEntity;
import com.example.gymapp.mappers.impl.ExerciseInstanceMapper;
import com.example.gymapp.mappers.impl.WorkingSetMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import com.example.gymapp.repositories.WorkingSetRepository;
import com.example.gymapp.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ExerciseInstanceService {

    @Autowired
    ExerciseInstanceMapper exerciseInstanceMapper;

    @Autowired
    WorkingSetMapper workingSetMapper;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    @Autowired
    WorkingSetRepository workingSetRepository;

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

    public ExerciseInstanceDto createWorkingSetforExercise(UUID exerciseId, WorkingSetDto workingSetDto) {
        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exercise instance with the ID %s not found.", exerciseId.toString())));

        WorkingSetEntity newWorkingSetEntity = workingSetMapper.mapFromDto(workingSetDto);
        newWorkingSetEntity.setExerciseInstance(exerciseInstance);
        workingSetRepository.save(newWorkingSetEntity);
        exerciseInstance.getWorkingSets().add(newWorkingSetEntity);

        exerciseInstance.setWorkingSets(new ArrayList<>(exerciseInstance.getWorkingSets()));

        return exerciseInstanceMapper.mapToDto(exerciseInstance);
    }

    @Transactional
    public ExerciseInstanceDto updateWorkingSetById(UUID exerciseInstanceId, Long setId, WorkingSetDto workingSetDto) {
        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseInstanceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exercise instance with the ID %s not found.", exerciseInstanceId.toString())));

        WorkingSetEntity workingSet = workingSetRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Set with the ID %s not found.", setId.toString())));

        workingSet.setReps(workingSetDto.getReps());
        workingSet.setWeight(workingSetDto.getWeight());
        WorkingSetEntity savedEntity = workingSetRepository.save(workingSet);

        exerciseInstance.getWorkingSets().sort(Comparator.comparingLong(WorkingSetEntity::getId));


        return exerciseInstanceMapper.mapToDto(exerciseInstance);
    }

    @Transactional
    public ExerciseInstanceDto deleteWorkingSetById(UUID exerciseInstanceId, Long setId) {
        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseInstanceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exercise instance with the ID %s not found.", exerciseInstanceId.toString())));

        WorkingSetEntity workingSet = workingSetRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Set with the ID %s not found.", setId.toString())));

        workingSetRepository.deleteById(setId);

        exerciseInstance.getWorkingSets().remove(workingSet);

//        exerciseInstance.getWorkingSets().sort(Comparator.comparingLong(WorkingSetEntity::getId));


        return exerciseInstanceMapper.mapToDto(exerciseInstance);
    }

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
