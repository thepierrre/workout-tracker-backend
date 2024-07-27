package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.InstanceWorkingSetDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.mappers.impl.ExerciseInstanceMapper;
import com.example.gymapp.mappers.impl.InstanceWorkingSetMapper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.WorkingSetRepository;
import com.example.gymapp.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    InstanceWorkingSetMapper instanceWorkingSetMapper;

    @Autowired
    WorkoutMapper workoutMapper;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    @Autowired
    WorkingSetRepository workingSetRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    public ExerciseInstanceDto createWorkingSetforExercise(UUID exerciseId, InstanceWorkingSetDto instanceWorkingSetDto) {
        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exercise instance with the ID %s not found.", exerciseId.toString())));

        InstanceWorkingSetEntity newInstanceWorkingSetEntity = instanceWorkingSetMapper.mapFromDto(instanceWorkingSetDto);
        newInstanceWorkingSetEntity.setExerciseInstance(exerciseInstance);
        workingSetRepository.save(newInstanceWorkingSetEntity);
        exerciseInstance.getWorkingSets().add(newInstanceWorkingSetEntity);

        exerciseInstance.setWorkingSets(new ArrayList<>(exerciseInstance.getWorkingSets()));

        return exerciseInstanceMapper.mapToDto(exerciseInstance);
    }

    @Transactional
    public ExerciseInstanceDto updateWorkingSetById(UUID exerciseInstanceId, UUID setId, InstanceWorkingSetDto instanceWorkingSetDto) {
        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseInstanceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exercise instance with the ID %s not found.", exerciseInstanceId.toString())));

        InstanceWorkingSetEntity workingSet = workingSetRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Set with the ID %s not found.", setId.toString())));

        workingSet.setReps(instanceWorkingSetDto.getReps());
        workingSet.setWeight(instanceWorkingSetDto.getWeight());
        workingSetRepository.save(workingSet);

        exerciseInstance.getWorkingSets().sort(Comparator.comparing(InstanceWorkingSetEntity::getCreationTimedate));

        return exerciseInstanceMapper.mapToDto(exerciseInstance);
    }

    @Transactional
    public ExerciseInstanceDto deleteWorkingSetById(UUID exerciseInstanceId, UUID setId) {
        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseInstanceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Exercise instance with the ID %s not found.", exerciseInstanceId.toString())));

        InstanceWorkingSetEntity workingSet = workingSetRepository.findById(setId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Set with the ID %s not found.", setId.toString())));

        workingSetRepository.deleteById(setId);
        exerciseInstance.getWorkingSets().remove(workingSet);

        return exerciseInstanceMapper.mapToDto(exerciseInstance);
    }

    public void deleteById(UUID exerciseInstanceId) {
        ExerciseInstanceEntity exerciseInstance = exerciseInstanceRepository.findById(exerciseInstanceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Exercise instance with the ID %s not found.", exerciseInstanceId.toString())));

        exerciseInstanceRepository.deleteById(exerciseInstanceId);
    }

    public ExerciseInstanceDto addExerciseToWorkout(UUID workoutId, String username, ExerciseTypeDto exerciseTypeDto) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        WorkoutEntity workoutEntity = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Workout with the ID \"%s\" not found.", workoutId)));

        ExerciseInstanceEntity exerciseToAdd = new ExerciseInstanceEntity();
        exerciseToAdd.setExerciseTypeName(exerciseTypeDto.getName());
        exerciseToAdd.setWorkout(workoutEntity);

        List<InstanceWorkingSetEntity> workingSets = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            InstanceWorkingSetEntity workingSet = new InstanceWorkingSetEntity();
            workingSet.setReps((short) 10);
            workingSet.setWeight((short) 30);
            workingSet.setExerciseInstance(exerciseToAdd);
            workingSets.add(workingSet);
        }

        exerciseToAdd.setWorkingSets(workingSets);
        workoutEntity.getExerciseInstances().add(exerciseToAdd);

        exerciseToAdd.setWorkout(workoutEntity);
        exerciseInstanceRepository.save(exerciseToAdd);
        return exerciseInstanceMapper.mapToDto(exerciseToAdd);
    }
}
