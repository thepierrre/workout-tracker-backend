package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.dto.WorkoutRequestDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return user.getWorkouts().stream()
                .map(workoutEntity -> workoutMapper.mapToDto(workoutEntity)).toList();
    }

    public Optional<WorkoutEntity> findById(UUID id) {
        return workoutRepository.findById(id);
    }

    @Transactional
    public WorkoutDto createWorkout(WorkoutDto workoutDto, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        RoutineEntity trainingRoutine = routineRepository.findByName(workoutDto.getRoutineName())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with the name \"%s\" not found.", workoutDto.getRoutineName())));

        WorkoutEntity workoutEntity = workoutMapper.mapFromDto(workoutDto);
        workoutEntity.setCreationDate(LocalDate.now());
        workoutEntity.setUser(user);
        workoutEntity.setRoutineName(workoutDto.getRoutineName());

        List<ExerciseInstanceEntity> exerciseInstances = new ArrayList<>();

        List<ExerciseTypeEntity> exerciseTypes = trainingRoutine.getExerciseTypes();
        
        for (ExerciseTypeEntity exerciseType : trainingRoutine.getExerciseTypes()) {
            ExerciseInstanceEntity exerciseInstance = new ExerciseInstanceEntity();
            exerciseInstance.setExerciseType(exerciseType);
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

    public void deleteById(UUID workoutId) {

        WorkoutEntity workout = workoutRepository.findById(workoutId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Workout with the ID %s not found.", workoutId.toString())));

        workoutRepository.deleteById(workoutId);

    }

    public void deleteAll() {
        workoutRepository.deleteAll();
    }
}
