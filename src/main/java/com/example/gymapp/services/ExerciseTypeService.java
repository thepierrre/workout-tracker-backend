package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.errorHandling.CustomServiceException;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.CategoryMapper;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    RoutineMapper routineMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RoutineRepository routineRepository;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    public ExerciseTypeService(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public ExerciseTypeDto createExercise(ExerciseTypeDto exerciseTypeDto, String username) {
        try {
            UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(String.format(
                            "User with the username \"%s\" not found.", username)));

            Optional<ExerciseTypeEntity> existingExerciseType = exerciseTypeRepository.findByUserAndName(user, exerciseTypeDto.getName());

            if (existingExerciseType.isPresent()) {
                throw new ConflictException(
                        "Exercise with the name '" + exerciseTypeDto.getName() + "' already exists.");
            }

            ExerciseTypeEntity exerciseTypeEntity = exerciseTypeMapper.mapFromDto(exerciseTypeDto);
            exerciseTypeEntity.setUser(user);

            if (exerciseTypeDto.getCategories() != null && !exerciseTypeDto.getCategories().isEmpty()) {
                List<CategoryEntity> categories = exerciseTypeDto.getCategories().stream()
                        .map(categoryDto -> categoryRepository.findById(categoryDto.getId())
                                .orElseThrow(() -> new EntityNotFoundException(String.format(
                                        "Category with the ID %s not found.", categoryDto.getId().toString()))))
                        .collect(Collectors.toList());
                exerciseTypeEntity.setCategories(categories);
            } else {
                exerciseTypeEntity.setCategories(new ArrayList<>());
            }

            ExerciseTypeEntity savedEntity = exerciseTypeRepository.save(exerciseTypeEntity);
            user.getExerciseTypes().add(savedEntity);
            userRepository.save(user);

            return exerciseTypeMapper.mapToDto(savedEntity);
        } catch (UsernameNotFoundException | ConflictException | EntityNotFoundException e) {
            throw e;
        }
        catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Couldn't create a new exercise due to an unexpected error.", e);
        }


    }

    public List<ExerciseTypeDto> findAll() {

        return exerciseTypeRepository.findAll().stream()
                .map(exerciseType -> exerciseTypeMapper.mapToDto(exerciseType)).toList();
    }

    public List<ExerciseTypeDto> findAllForUser(String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        return user.getExerciseTypes().stream()
                .map(exerciseType -> exerciseTypeMapper.mapToDto(exerciseType)).toList();

    }

    @Transactional
    public void deleteById(UUID exerciseTypeId) {
        ExerciseTypeEntity exerciseType= exerciseTypeRepository.findById(exerciseTypeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Exercise type with the ID %s not found.", exerciseTypeId.toString())));

        for (RoutineEntity routine : exerciseType.getRoutines()) {
            routine.getExerciseTypes().remove(exerciseType);
            routineRepository.save(routine);
        }

        for (CategoryEntity category : exerciseType.getCategories()) {
            category.getExerciseTypes().remove(exerciseType);
            categoryRepository.save(category);
        }

        exerciseType.getUser().getExerciseTypes().remove(exerciseType);

        exerciseTypeRepository.deleteById(exerciseTypeId);
    }

    public void deleteAll() {
        exerciseTypeRepository.deleteAll();
    }


    public ExerciseTypeDto updateById(UUID id, ExerciseTypeDto exerciseTypeDto) {

        ExerciseTypeEntity existingExercise = exerciseTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Exercise type with the ID %s not found.", id.toString())));

        existingExercise.setName(exerciseTypeDto.getName());
        existingExercise.setId(exerciseTypeDto.getId());
        existingExercise.setUser(existingExercise.getUser());
        existingExercise.setRoutines(existingExercise.getRoutines());

        List<CategoryEntity> newCategories = exerciseTypeDto.getCategories().stream()
                .map(categoryDto -> categoryMapper.mapFromDto(categoryDto)).toList();

        existingExercise.getCategories().clear();
        existingExercise.getCategories().addAll(newCategories);

        ExerciseTypeEntity updatedExercise = exerciseTypeRepository.save(existingExercise);

        return exerciseTypeMapper.mapToDto(updatedExercise);


    }
}
