package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.exceptions.ConflictException;
import com.example.gymapp.mappers.impl.CategoryMapper;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoutineRepository;
import com.example.gymapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExerciseTypeService {

    @Autowired
    ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;


    public ExerciseTypeDto createExercise(ExerciseTypeDto exerciseTypeDto, String username) {
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
    }

    public List<ExerciseTypeDto> findAllDefault(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with the username \"%s\" not found.", username)));

        List<ExerciseTypeEntity> defaultExercises = exerciseTypeRepository.findAllDefault();
        return defaultExercises.stream()
                .map(exerciseTypeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ExerciseTypeDto> findAllForUser(String username) {
        List<ExerciseTypeEntity> exerciseTypes = exerciseTypeRepository.findByUserUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with the username \"%s\" not found.", username)));

        if (!exerciseTypes.isEmpty()) {
            return exerciseTypes.stream()
                    .map(exerciseTypeMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    @Transactional
    public void deleteById(UUID exerciseTypeId) {
        ExerciseTypeEntity exerciseType= exerciseTypeRepository.findById(exerciseTypeId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Exercise type with the ID %s not found.", exerciseTypeId.toString())));

        //TODO
//        for (RoutineEntity routine : exerciseType.getRoutines()) {
//            routine.getExerciseTypes().remove(exerciseType);
//            routineRepository.save(routine);
//        }

        for (CategoryEntity category : exerciseType.getCategories()) {
            category.getExerciseTypes().remove(exerciseType);
            categoryRepository.save(category);
        }

        exerciseType.getUser().getExerciseTypes().remove(exerciseType);

        exerciseTypeRepository.deleteById(exerciseTypeId);
    }


    public ExerciseTypeDto updateById(UUID id, ExerciseTypeDto exerciseTypeDto, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(
                        "User with the username \"%s\" not found.", username)));

        Optional<ExerciseTypeEntity> existingExerciseType = Optional.ofNullable(exerciseTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Exercise type with the ID %s not found.", id.toString()))));

        Optional<ExerciseTypeEntity> existingExerciseTypeWithName = exerciseTypeRepository.findByUserAndName(user, exerciseTypeDto.getName());

        if (existingExerciseTypeWithName.isPresent() && !existingExerciseTypeWithName.get().getId().equals(id)) {
            throw new ConflictException(
                    "Exercise with the name '" + exerciseTypeDto.getName() + "' already exists.");
        }

        existingExerciseType.get().setName(exerciseTypeDto.getName());
        existingExerciseType.get().setId(exerciseTypeDto.getId());
        existingExerciseType.get().setUser(existingExerciseType.get().getUser());

        //TODO
//        existingExerciseType.get().setRoutines(existingExerciseType.get().getRoutines());

        List<CategoryEntity> newCategories = exerciseTypeDto.getCategories().stream()
                .map(categoryDto -> categoryMapper.mapFromDto(categoryDto)).toList();

        existingExerciseType.get().getCategories().clear();
        existingExerciseType.get().getCategories().addAll(newCategories);

        ExerciseTypeEntity updatedExercise = exerciseTypeRepository.save(existingExerciseType.get());

        return exerciseTypeMapper.mapToDto(updatedExercise);
    }

    public void createDefaultExercisesIfNotExistent() {
        Map<String, Map<String, List<String>>> exercisesWithCategories = Map.ofEntries(
        Map.entry("Barbell bench press", Map.of("Barbell", List.of("Chest", "Anterior delts", "Triceps"))),
        Map.entry("Incline barbell bench press", Map.of("Barbell", List.of("Chest", "Anterior delts", "Triceps"))),
        Map.entry("Dumbbell bench press", Map.of("Dumbbells", List.of("Chest", "Anterior delts", "Triceps"))),
        Map.entry("Incline dumbbell bench press", Map.of("Dumbbells", List.of("Chest", "Anterior delts", "Triceps"))),
        Map.entry("Barbell squats", Map.of("Barbell", List.of("Glutes", "Quadriceps", "Core"))),
        Map.entry("Barbell deadlifts", Map.of("Barbell", List.of("Glutes", "Hamstrings", "Lower back", "Core"))),
        Map.entry("Standing calf raises", Map.of("Machine", List.of("Calves"))),
        Map.entry("Seated calf raises", Map.of("Machine", List.of("Calves"))),
        Map.entry("Dumbbell shoulder press", Map.of("Dumbbells", List.of("Anterior delts"))),
        Map.entry("Dumbbell rear delt flies", Map.of("Dumbbells", List.of("Posterior delts"))),
        Map.entry("Dumbbell shoulder lateral raise", Map.of("Dumbbells", List.of("Lateral delts"))),
        Map.entry("Barbell standing rows", Map.of("Barbell", List.of("Lats", "Traps", "Rhomboids", "Posterior delts"))),
        Map.entry("Machine seated rows", Map.of("Machine", List.of("Lats", "Traps", "Rhomboids", "Posterior delts"))),
        Map.entry("Pull-downs", Map.of("Bar", List.of("Lats", "Traps", "Rhomboids"))),
        Map.entry("Pull-ups", Map.of("Bar", List.of("Lats", "Traps", "Rhomboids", "Core"))),
        Map.entry("Machine chest flies", Map.of("Machine", List.of("Chest"))),
        Map.entry("Dumbbell biceps curls", Map.of("Dumbbells", List.of("Biceps"))),
        Map.entry("Barbell biceps curls", Map.of("Barbell", List.of("Biceps")))
        );

        exercisesWithCategories.forEach((exerciseName, details) -> {
            details.forEach((equipment, categoryNames) -> {
                createExerciseTypeIfNotExistent(exerciseName, categoryNames, equipment, true);
            });
        });
    }

    private void createExerciseTypeIfNotExistent(String name, List<String> categoryNames, String equipment, Boolean isDefault) {
        Optional<ExerciseTypeEntity> existingExerciseType = exerciseTypeRepository.findByName(name);
        if (existingExerciseType.isEmpty()) {
            List<CategoryEntity> categories = categoryService.findCategoriesByNames(categoryNames);
            ExerciseTypeEntity exerciseType = new ExerciseTypeEntity(name, categories, equipment, isDefault);
            exerciseTypeRepository.save(exerciseType);
        }
    }
}
