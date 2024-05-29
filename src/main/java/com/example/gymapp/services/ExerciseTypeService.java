package com.example.gymapp.services;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    RoutineMapper routineMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ExerciseTypeService(ExerciseTypeRepository exerciseTypeRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public ExerciseTypeDto createExercise(ExerciseTypeDto exerciseTypeDto, String username) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        ExerciseTypeEntity exerciseTypeEntity = exerciseTypeMapper.mapFromDto(exerciseTypeDto);
        exerciseTypeEntity.setUser(user);

        if (exerciseTypeDto.getCategories() != null && !exerciseTypeDto.getCategories().isEmpty()) {
            List<CategoryEntity> categories = exerciseTypeDto.getCategories().stream()
                    .map(categoryDto -> categoryRepository.findById(categoryDto.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryDto.getId())))
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

    public List<ExerciseTypeEntity> findAll() {
        return exerciseTypeRepository.findAll();
    }

    @Transactional
    public void deleteById(UUID id) {
        ExerciseTypeEntity exerciseType= exerciseTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercise type not found."));

        for (CategoryEntity category : exerciseType.getCategories()) {
            category.getExerciseTypes().remove(exerciseType);
            categoryRepository.save(category);
        }

        UserEntity user = exerciseType.getUser();
        if (user != null) {
            user.getExerciseTypes().remove(exerciseType);
            userRepository.save(user);
        }

        exerciseTypeRepository.deleteById(id);
    }

    public void deleteAll() {
        exerciseTypeRepository.deleteAll();
    }

    public boolean isExists(UUID id) {
        return exerciseTypeRepository.existsById(id);
    }

    public ExerciseTypeDto update(UUID id, ExerciseTypeDto exerciseTypeDto) {
        exerciseTypeDto.setId(id);
        return exerciseTypeRepository.findById(id).map(existingExercise -> {
            Optional.ofNullable(exerciseTypeDto.getName()).ifPresent(existingExercise::setName);
            exerciseTypeRepository.save(existingExercise);
            return exerciseTypeMapper.mapToDto(existingExercise);
        }).orElseThrow(() -> new RuntimeException("Exercise does not exist"));
    }
}
