package com.example.gymapp.services;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineMapper routineMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    public RoutineDto createRoutine(RoutineDto routineDto, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        boolean routineExists = user.getRoutines().stream()
                .anyMatch(routine -> routine.getName().equalsIgnoreCase(routineDto.getName()));

        if (routineExists) {
            throw new IllegalArgumentException(" A training routine with this name already exists.");
        }

        RoutineEntity routineEntity = routineMapper.mapFromDto(routineDto);
        routineEntity.setUser(user);

        if (routineDto.getExerciseTypes() != null && !routineDto.getExerciseTypes().isEmpty()) {
            List<ExerciseTypeEntity> exerciseTypes = routineDto.getExerciseTypes().stream()
                    .map(exerciseTypeDto -> exerciseTypeRepository.findById(exerciseTypeDto.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Category not found: " + exerciseTypeDto.getId())))
                    .collect(Collectors.toList());
            routineEntity.setExerciseTypes(exerciseTypes);
        } else {
            routineEntity.setExerciseTypes(new ArrayList<>());
        }

        RoutineEntity savedRoutineEntity = routineRepository.save(routineEntity);
        user.getRoutines().add(savedRoutineEntity);
        userRepository.save(user);

        return routineMapper.mapToDto(savedRoutineEntity);



//        TrainingRoutineEntity trainingRoutineEntity = trainingRoutineMapper.mapFromDto(trainingRoutineDto);
//
//        trainingRoutineEntity.setExerciseTypes(trainingRoutineEntity.getExerciseTypes());
//
//        TrainingRoutineEntity savedTrainingRoutineEntity = trainingRoutineRepository.save(trainingRoutineEntity);
//
//        return trainingRoutineMapper.mapToDto(savedTrainingRoutineEntity);
    }

    public List<RoutineDto> findAll() {
        return routineRepository.
                findAll().stream().map(routineMapper::mapToDto).
                toList(); }

    public void deleteById(UUID id) {
        routineRepository.deleteById(id);
    }

    public void deleteAll() {
        routineRepository.deleteAll();
    }
}
