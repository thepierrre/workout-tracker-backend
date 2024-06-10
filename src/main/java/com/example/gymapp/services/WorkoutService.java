package com.example.gymapp.services;

import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.dto.WorkoutRequestDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
        try {
            List<WorkoutEntity> workoutEntities = workoutRepository.findByUserUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("User with the username \"%s\" not found.", username)));

            if (!workoutEntities.isEmpty()) {
                return workoutEntities.stream()
                        .map(workoutMapper::mapToDto)
                        .collect(Collectors.toList());
            }
            return List.of();

        } catch (UsernameNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't find workouts for the user due to an unexpected error.", e);
        }

    }

    public Optional<WorkoutDto> findById(UUID id) {

        WorkoutEntity workoutEntity = workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Workout with the ID \"%s\" not found.", id)));

        return Optional.ofNullable(workoutMapper.mapToDto(workoutEntity));
    }

    @Transactional
    public WorkoutDto createWorkout(WorkoutDto workoutDto, String username) {

        try {
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

        } catch (UsernameNotFoundException | EntityNotFoundException e) {
            throw e;
        }
            catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't create a new routine due to an unexpected error.", e);
        }



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
