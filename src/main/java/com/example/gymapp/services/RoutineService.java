package com.example.gymapp.services;

import com.example.gymapp.domain.dto.BlueprintWorkingSetDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.RoutineExerciseDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.BlueprintWorkingSetMapper;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineMapper routineMapper;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;

    @Autowired
    private BlueprintWorkingSetRepository workingSetRepository;

    @Autowired
    private BlueprintWorkingSetMapper workingSetMapper;

    private RoutineExerciseEntity createExerciseForRoutine(UserEntity user, RoutineExerciseDto routineExerciseDto, int order) {
        ExerciseTypeEntity exerciseTypeEntity = exerciseTypeRepository.findByUserAndName(user, routineExerciseDto.getName())
                .orElseGet(() -> exerciseTypeRepository.findByDefaultAndName(routineExerciseDto.getName())
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Exercise type with the name %s not found", routineExerciseDto.getName()
                        ))));

        RoutineExerciseEntity routineExerciseEntity = new RoutineExerciseEntity();
        routineExerciseEntity.setName(exerciseTypeEntity.getName());
        routineExerciseEntity.setExerciseOrder((short) order);

        List<BlueprintWorkingSetEntity> workingSetEntities = new ArrayList<>();
        if (routineExerciseDto.getWorkingSets() != null) {
            workingSetEntities = routineExerciseDto.getWorkingSets().stream()
                    .map(blueprintWorkingSetDto -> {
                        BlueprintWorkingSetEntity workingSetEntity = workingSetMapper.mapFromDto(blueprintWorkingSetDto);
                        workingSetEntity.setRoutineExercise(routineExerciseEntity);
                        return workingSetEntity;
                    }).collect(Collectors.toList());
        }

        routineExerciseEntity.setWorkingSets(workingSetEntities);
        return routineExerciseEntity;
    }

    @Transactional
    public RoutineDto createRoutine(RoutineDto routineDto, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        Optional<RoutineEntity> existingRoutine = routineRepository.findByUserAndName(user, routineDto.getName());

        if (existingRoutine.isPresent()) {
            throw new ConflictException(
                    "Routine with the name '" + routineDto.getName() + "' already exists.");
        }

        RoutineEntity routineEntity = routineMapper.mapFromDto(routineDto);
        routineEntity.setUser(user);

        if (!routineDto.getRoutineExercises().isEmpty()) {
            List<RoutineExerciseEntity> routineExercises = IntStream.range(0, routineDto.getRoutineExercises().size())
                            .mapToObj(index -> {
                                RoutineExerciseDto routineExerciseDto = routineDto.getRoutineExercises().get(index);
                                return createExerciseForRoutine(user, routineExerciseDto, index);
                                    })
                    .collect(Collectors.toList());

            routineExercises.forEach(routineExercise -> {
                routineExercise.setRoutine(routineEntity);
                routineExerciseRepository.save(routineExercise);
            });

            routineEntity.setRoutineExercises(routineExercises);
        } else {
            routineEntity.setRoutineExercises(new ArrayList<>());
        }

        RoutineEntity savedRoutineEntity = routineRepository.save(routineEntity);
        user.getRoutines().add(savedRoutineEntity);
        userRepository.save(user);

        return routineMapper.mapToDto(savedRoutineEntity);
    }

    public List<RoutineDto> findAllForUser(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        return user.getRoutines().stream()
                .map(routineEntity -> routineMapper.mapToDto(routineEntity)).toList();
    }

    private void updateRoutineExercise(RoutineExerciseEntity existingExercise, RoutineExerciseDto routineExerciseDto, int order) {
        existingExercise.setName(routineExerciseDto.getName());
        existingExercise.setExerciseOrder((short) order);

        // If a working set from the old (existing) exercise is not in the DTO, delete it.
        existingExercise.getWorkingSets().removeIf(existingWorkingSet ->
                routineExerciseDto.getWorkingSets().stream().noneMatch(workingSetDto ->
                        workingSetDto.getId() != null && workingSetDto.getId().equals(existingWorkingSet.getId())
                )
        );

        routineExerciseDto.getWorkingSets().forEach(workingSetDto -> {
            if (workingSetDto.getId() != null) {
                // Update the existing set.
                BlueprintWorkingSetEntity existingWorkingSet = workingSetRepository.findById(workingSetDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Working set with ID %s not found", workingSetDto.getId())));

                existingWorkingSet.setReps(workingSetDto.getReps());
                existingWorkingSet.setWeight(workingSetDto.getWeight());
                workingSetRepository.save(existingWorkingSet);
            } else {
                // Create a new set.
                BlueprintWorkingSetEntity newWorkingSet = workingSetMapper.mapFromDto(workingSetDto);
                newWorkingSet.setRoutineExercise(existingExercise);
                existingExercise.getWorkingSets().add(newWorkingSet);
                workingSetRepository.save(newWorkingSet);
            }
        });
    }

    @Transactional
    public RoutineDto updateById(UUID id, RoutineDto routineDto, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        RoutineEntity existingRoutine = routineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Routine with the ID %s not found.", id.toString())));

        Optional<RoutineEntity> existingRoutineWithName = routineRepository.findByUserAndName(user, routineDto.getName());

        if (existingRoutineWithName.isPresent() && !existingRoutineWithName.get().getId().equals(id)) {
            throw new ConflictException(
                    "Routine with the name '" + routineDto.getName() + "' already exists.");
        }

        existingRoutine.setName(routineDto.getName());

        // If exercise from the old (existing) routine is not in the DTO, delete it.
        existingRoutine.getRoutineExercises().removeIf(existingExercise ->
                routineDto.getRoutineExercises().stream().noneMatch(ex ->
                        ex.getId() != null && ex.getId().equals(existingExercise.getId())
                )
        );

        for (int i = 0; i < routineDto.getRoutineExercises().size(); i++) {
            RoutineExerciseDto routineExerciseDto = routineDto.getRoutineExercises().get(i);

            if (routineExerciseDto.getId() != null) {
                RoutineExerciseEntity existingExercise = routineExerciseRepository.findById(routineExerciseDto.getId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Routine exercise with ID %s not found", routineExerciseDto.getId())));

                updateRoutineExercise(existingExercise, routineExerciseDto, i);
            } else {
                RoutineExerciseEntity newExercise = createExerciseForRoutine(user, routineExerciseDto, i);
                newExercise.setRoutine(existingRoutine);
                existingRoutine.getRoutineExercises().add(newExercise);
            }
        }

        existingRoutine.getRoutineExercises().sort(Comparator.comparing(RoutineExerciseEntity::getExerciseOrder));
        RoutineEntity updatedRoutine = routineRepository.save(existingRoutine);
        return routineMapper.mapToDto(updatedRoutine);
    }

    public void deleteById(UUID routineId) {
        routineRepository.findById(routineId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Routine with the ID %s not found.", routineId)));

        routineRepository.deleteById(routineId);
    }

    public void createRoutinesForExampleUserIfNonExistent(String username) {
        BlueprintWorkingSetDto workingSet1 = new BlueprintWorkingSetDto((short) 10, 12.0);
        BlueprintWorkingSetDto workingSet2 = new BlueprintWorkingSetDto((short) 11, 40.0);
        BlueprintWorkingSetDto workingSet3 = new BlueprintWorkingSetDto((short) 11, 60.0);
        BlueprintWorkingSetDto workingSet4 = new BlueprintWorkingSetDto((short) 10, 45.0);
        BlueprintWorkingSetDto workingSet5 = new BlueprintWorkingSetDto((short) 10, 25.0);

        RoutineExerciseDto exercise1 = new RoutineExerciseDto(
                "Barbell bench press",
                Collections.nCopies(4, workingSet4)
        );
        RoutineExerciseDto exercise2 = new RoutineExerciseDto(
                "Barbell squats",
                Collections.nCopies(3, workingSet3)
        );
        RoutineExerciseDto exercise3 = new RoutineExerciseDto(
                "Pull-ups",
                Collections.nCopies(4, workingSet2)
        );
        RoutineExerciseDto exercise4 = new RoutineExerciseDto(
                "Dumbbell biceps curls",
                Collections.nCopies(4, workingSet5)
        );
        RoutineExerciseDto exercise5 = new RoutineExerciseDto(
                "Barbell deadlifts",
                Collections.nCopies(4, workingSet3)
        );
        RoutineExerciseDto exercise6 = new RoutineExerciseDto(
                "Leg press",
                Collections.nCopies(4, workingSet4)
        );
        RoutineExerciseDto exercise7 = new RoutineExerciseDto(
                "Dumbbell shoulder press",
                Collections.nCopies(3, workingSet1)
        );
        RoutineExerciseDto exercise8 = new RoutineExerciseDto(
                "Standing calf raises",
                Collections.nCopies(3, workingSet1)
        );

        RoutineDto routine1 = new RoutineDto(
                "Full Body Workout A",
                List.of(exercise1, exercise2, exercise3, exercise4));

        RoutineDto routine2 = new RoutineDto(
                "Full Body Workout B",
                List.of(exercise5, exercise6, exercise7, exercise8));

        createRoutine(routine1, username);
        createRoutine(routine2, username);
    }
}
