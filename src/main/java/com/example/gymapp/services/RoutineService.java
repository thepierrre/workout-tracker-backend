package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    public RoutineDto createRoutine(RoutineDto routineDto, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

         Optional<RoutineEntity> existingRoutine = user.getRoutines().stream()
                .filter(routine -> routine.getName().equalsIgnoreCase(routineDto.getName()))
                .findFirst();

        if (existingRoutine.isPresent()) {
            throw new IllegalArgumentException(String.format(
                    " A training routine with the name \"%s\" already exists.",
                    existingRoutine.get().getName()));
        }

        RoutineEntity routineEntity = routineMapper.mapFromDto(routineDto);
        routineEntity.setUser(user);

        if (routineDto.getExerciseTypes() != null && !routineDto.getExerciseTypes().isEmpty()) {
            List<ExerciseTypeEntity> exerciseTypes = routineDto.getExerciseTypes().stream()
                    .map(exerciseTypeDto -> exerciseTypeRepository.findById(exerciseTypeDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException(String.format(
                                    "Exercise type with the ID %s not found.", exerciseTypeDto.getId().toString()))))
                    .collect(Collectors.toList());
            routineEntity.setExerciseTypes(exerciseTypes);
        } else {
            routineEntity.setExerciseTypes(new ArrayList<>());
        }

        RoutineEntity savedRoutineEntity = routineRepository.save(routineEntity);
        user.getRoutines().add(savedRoutineEntity);
        userRepository.save(user);

        return routineMapper.mapToDto(savedRoutineEntity);
    }

    public List<RoutineDto> findAll() {
        return routineRepository.
                findAll().stream().map(routineMapper::mapToDto).
                toList(); }

    public List<RoutineDto> findAllForUser(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        return user.getRoutines().stream()
                .map(routineEntity -> routineMapper.mapToDto(routineEntity)).toList();

    }

    public RoutineDto updateById(UUID id, RoutineDto routineDto) {

        RoutineEntity existingRoutine = routineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Routine type with the ID %s not found.", id.toString())));

        existingRoutine.setId(routineDto.getId());
        existingRoutine.setName(routineDto.getName());

        existingRoutine.setUser(existingRoutine.getUser());

        List<ExerciseTypeEntity> newExercises = routineDto.getExerciseTypes().stream()
                .map(exerciseTypeDto -> exerciseTypeMapper.mapFromDto(exerciseTypeDto)).toList();

        existingRoutine.getExerciseTypes().clear();
        existingRoutine.getExerciseTypes().addAll(newExercises);

        RoutineEntity updatedRoutine = routineRepository.save(existingRoutine);

        return routineMapper.mapToDto(updatedRoutine);


    }

    public void deleteById(UUID routineId) {

        RoutineEntity routine = routineRepository.findById(routineId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Routine with the ID %s not found.", routineId.toString())));

        routineRepository.deleteById(routineId);
    }

    public void deleteAll() {
        routineRepository.deleteAll();
    }
}
