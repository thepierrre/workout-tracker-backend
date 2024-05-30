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
    WorkoutMapper workoutMapper;

    public List<WorkoutDto> findAll() {
        return workoutRepository.findAll().stream().map(workoutMapper::mapToDto).toList();
    }

    public Optional<WorkoutEntity> findById(UUID id) {
        return workoutRepository.findById(id);
    }

    @Transactional
    public WorkoutDto createWorkout(WorkoutDto workoutDto, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        RoutineEntity trainingRoutine = routineRepository.findByName(workoutDto.getRoutineName())
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        WorkoutEntity workoutEntity = workoutMapper.mapFromDto(workoutDto);
        workoutEntity.setCreationDate(LocalDate.now());
        workoutEntity.setUser(user);
        workoutEntity.setRoutineName(workoutDto.getRoutineName());
//        System.out.println(workoutEntity);

        List<ExerciseInstanceEntity> exerciseInstances = new ArrayList<>();

        List<ExerciseTypeEntity> exerciseTypes = trainingRoutine.getExerciseTypes();

        // the for loop isn't entered at all
        for (ExerciseTypeEntity exerciseType : trainingRoutine.getExerciseTypes()) {
//            System.out.println("inside the loop");
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
