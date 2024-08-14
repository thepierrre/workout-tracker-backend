package com.example.gymapp.services;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.RoutineExerciseDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.BlueprintWorkingSetMapper;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoutineExerciseRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private BlueprintWorkingSetMapper blueprintWorkingSetMapper;

    private RoutineExerciseEntity createExerciseForRoutine(UserEntity user, RoutineExerciseDto routineExerciseDto) {
        ExerciseTypeEntity exerciseTypeEntity = exerciseTypeRepository.findByUserAndName(user, routineExerciseDto.getName())
                .orElseGet(() -> exerciseTypeRepository.findByDefaultAndName(routineExerciseDto.getName())
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Exercise type with the name %s not found", routineExerciseDto.getName()
                        ))));

        RoutineExerciseEntity routineExerciseEntity = new RoutineExerciseEntity();
        routineExerciseEntity.setName(exerciseTypeEntity.getName());
        routineExerciseEntity.setExerciseType(exerciseTypeEntity);

        List<BlueprintWorkingSetEntity> workingSetEntities = new ArrayList<>();
        if (routineExerciseDto.getWorkingSets() != null) {
            workingSetEntities = routineExerciseDto.getWorkingSets().stream()
                    .map(blueprintWorkingSetDto -> {
                        BlueprintWorkingSetEntity workingSetEntity = blueprintWorkingSetMapper.mapFromDto(blueprintWorkingSetDto);
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
            List<RoutineExerciseEntity> routineExercises = routineDto.getRoutineExercises().stream()
                    .map(routineExerciseDto -> createExerciseForRoutine(user, routineExerciseDto)).collect(Collectors.toList());

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

        existingRoutine.getRoutineExercises().clear();

        if (!routineDto.getRoutineExercises().isEmpty()) {
            List<RoutineExerciseEntity> routineExercises = routineDto.getRoutineExercises().stream()
                    .map(routineExerciseDto -> createExerciseForRoutine(user, routineExerciseDto)).toList();

            routineExercises.forEach(routineExercise -> {
//                Optional<ExerciseTypeEntity> exercise = exerciseTypeRepository.findByUserAndName(user, routineExercise.getName());
//                if (exercise.isPresent()) {
//                    routineExercise.setExerciseType(exercise.get());
//                }
                routineExercise.setRoutine(existingRoutine);
                routineExerciseRepository.save(routineExercise);
            });
            existingRoutine.getRoutineExercises().addAll(routineExercises);
        }

        RoutineEntity updatedRoutine = routineRepository.save(existingRoutine);

        return routineMapper.mapToDto(updatedRoutine);
    }

    public void deleteById(UUID routineId) {
        routineRepository.findById(routineId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Routine with the ID %s not found.", routineId)));

        routineRepository.deleteById(routineId);
    }
}
