package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.WorkingSetDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.ExerciseInstanceRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoutineRepository routineRepository;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    @Autowired
    WorkoutMapper workoutMapper;

    public List<WorkoutDto> findAll() {
        return workoutRepository.findAll().stream().map(workoutMapper::mapToDto).toList();
    }

    public List<WorkoutDto> findAllForUser(String username) {
        List<WorkoutEntity> workoutEntities = workoutRepository.findByUserUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with the username \"%s\" not found.", username)));

        if (!workoutEntities.isEmpty()) {
            return workoutEntities.stream()
                    .map(workoutMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public Optional<WorkoutDto> findById(UUID id) {
        WorkoutEntity workoutEntity = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Workout with the ID \"%s\" not found.", id)));

        return Optional.ofNullable(workoutMapper.mapToDto(workoutEntity));
    }

    @Transactional
    public WorkoutDto createWorkout(WorkoutDto workoutDto, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        RoutineEntity trainingRoutine = routineRepository.findByUserAndName(user, workoutDto.getRoutineName())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Routine with the name \"%s\" not found for user \"%s\".", workoutDto.getRoutineName(), username)));

        WorkoutEntity workoutEntity = workoutMapper.mapFromDto(workoutDto);
        workoutEntity.setCreationDate(workoutDto.getCreationDate());
        workoutEntity.setUser(user);
        workoutEntity.setRoutineName(workoutDto.getRoutineName());

        List<ExerciseInstanceEntity> exerciseInstances = new ArrayList<>();

        for (ExerciseTypeEntity exerciseType : trainingRoutine.getExerciseTypes()) {
            ExerciseInstanceEntity exerciseInstance = new ExerciseInstanceEntity();
            exerciseInstance.setExerciseTypeName(exerciseType.getName());
            exerciseInstance.setWorkout(workoutEntity);


            List<WorkingSetEntity> workingSets = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                WorkingSetEntity workingSet = new WorkingSetEntity();
                workingSet.setReps((short) 0);
                workingSet.setWeight((short) 0);
                workingSet.setExerciseInstance(exerciseInstance);
                workingSets.add(workingSet);
            }

            exerciseInstance.setWorkingSets(workingSets);
            exerciseInstances.add(exerciseInstance);
        }

        workoutEntity.setExerciseInstances(exerciseInstances);

        WorkoutEntity savedWorkoutEntity = workoutRepository.save(workoutEntity);

        return workoutMapper.mapToDto(savedWorkoutEntity);
    }

//    public WorkoutDto updateById(UUID id, WorkoutDto workoutDto) {
//        WorkoutEntity foundWorkoutEntity = workoutRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException(String.format(
//                        "Workout with the ID %s not found.", id.toString())));
//
//        WorkoutDto dto = workoutMapper.mapToDto(foundWorkoutEntity);
//        dto.setExerciseInstances(workoutDto.getExerciseInstances());
//        workoutRepository.save(workoutMapper.mapFromDto(dto));
//
//        return dto;
//
//    }

    @Transactional
    public WorkoutDto updateById(UUID id, WorkoutDto workoutDto) {
        WorkoutEntity foundWorkoutEntity = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Workout with the ID %s not found.", id.toString())));

        // Update exercise instances and working sets
        for (ExerciseInstanceDto updatedInstanceDto : workoutDto.getExerciseInstances()) {
            ExerciseInstanceEntity existingInstance = foundWorkoutEntity.getExerciseInstances().stream()
                    .filter(instance -> instance.getExerciseTypeName().equals(updatedInstanceDto.getExerciseTypeName()))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException(String.format(
                            "ExerciseInstance with the type name %s not found.", updatedInstanceDto.getExerciseTypeName())));

            // Update or add working sets
            for (WorkingSetDto updatedSetDto : updatedInstanceDto.getWorkingSets()) {
                if (updatedSetDto.getId() == null) {
                    // Add new working set
                    WorkingSetEntity newSet = new WorkingSetEntity();
                    newSet.setReps(updatedSetDto.getReps());
                    newSet.setWeight(updatedSetDto.getWeight());
                    newSet.setExerciseInstance(existingInstance);
                    existingInstance.getWorkingSets().add(newSet);
                } else {
                    // Update existing working set
                    WorkingSetEntity existingSet = existingInstance.getWorkingSets().stream()
                            .filter(set -> set.getId().equals(updatedSetDto.getId()))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException(String.format(
                                    "WorkingSet with the ID %s not found.", updatedSetDto.getId())));
                    existingSet.setReps(updatedSetDto.getReps());
                    existingSet.setWeight(updatedSetDto.getWeight());
                }
            }
        }

        // Save the updated workout entity
        WorkoutEntity updatedWorkoutEntity = workoutRepository.save(foundWorkoutEntity);

        return workoutMapper.mapToDto(updatedWorkoutEntity);
    }

    public void deleteById(UUID id) {
        WorkoutEntity workoutEntity = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Workout with the ID \"%s\" not found.", id)));

        workoutRepository.deleteById(id);
    }

    public void deleteAll() {
        workoutRepository.deleteAll();
    }
}
