package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
    WorkoutMapper workoutMapper;

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

    public List<WorkoutDto> findWorkoutsForUserForDate(String username, LocalDate date) {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new UsernameNotFoundException(String.format(
                    "User with the username \"%s\" not found.", username));
        }

        return workoutRepository.findByUserUsernameAndCreationDate(username, date)
                .stream()
                .map(workoutMapper::mapToDto)
                .collect(Collectors.toList());
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

        for (RoutineExerciseEntity routineExercise : trainingRoutine.getRoutineExercises()) {
            ExerciseInstanceEntity exerciseInstance = new ExerciseInstanceEntity();
            exerciseInstance.setExerciseTypeName(routineExercise.getName());
            exerciseInstance.setWorkout(workoutEntity);

            LocalDateTime now = LocalDateTime.now();

            List<InstanceWorkingSetEntity> workingSets = new ArrayList<>();
            for (BlueprintWorkingSetEntity blueprintWorkingSet : routineExercise.getWorkingSets()) {
                InstanceWorkingSetEntity workingSet = new InstanceWorkingSetEntity();
                workingSet.setReps(blueprintWorkingSet.getReps());
                workingSet.setWeight(blueprintWorkingSet.getWeight());
                workingSet.setCreationTimedate(now.plusNanos(workingSets.size() * 1000000L));
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
    public void deleteById(UUID id) {
        workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Workout with the ID \"%s\" not found.", id)));

        workoutRepository.deleteById(id);
    }
}
