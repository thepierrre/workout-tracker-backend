package com.example.gymapp.services;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
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

    public RoutineDto createRoutine(RoutineDto routineDto, String username) {
            UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format(
                            "User with the username \"%s\" not found.", username)));

            boolean routineExists = user.getRoutines().stream()
                    .anyMatch(routine -> routine.getName().equals(routineDto.getName()));

            if (routineExists) {
                throw new ConflictException(
                        "Routine with the name '" + routineDto.getName() + "' already exists.");
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

    public List<RoutineDto> findAllForUser(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        return user.getRoutines().stream()
                .map(routineEntity -> routineMapper.mapToDto(routineEntity)).toList();
    }

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

        existingRoutine.setId(routineDto.getId());
        existingRoutine.setName(routineDto.getName());

        existingRoutine.setUser(existingRoutine.getUser());

        List<ExerciseTypeEntity> newExercises = routineDto.getExerciseTypes().stream()
                .map(exerciseTypeDto -> exerciseTypeMapper.mapFromDto(exerciseTypeDto))
                .collect(Collectors.toList());

        existingRoutine.getExerciseTypes().clear();
        existingRoutine.setExerciseTypes(newExercises);

        RoutineEntity updatedRoutine = routineRepository.save(existingRoutine);

        return routineMapper.mapToDto(updatedRoutine);
    }

    public void deleteById(UUID routineId) {
        RoutineEntity routine = routineRepository.findById(routineId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format(
                                "Routine with the ID %s not found.", routineId.toString())));

        routineRepository.deleteById(routineId);
    }
}
