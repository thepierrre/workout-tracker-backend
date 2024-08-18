package com.example.gymapp.services;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.mappers.impl.CategoryMapper;
import com.example.gymapp.repositories.CategoryRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void createCategoriesIfNonExistent() {

        List<CategoryEntity> categories = new ArrayList<>(List.of(
                new CategoryEntity("Core", CategoryEntity.MuscleGroup.CORE),
                new CategoryEntity("Abs", CategoryEntity.MuscleGroup.CORE),
                new CategoryEntity("Deep core", CategoryEntity.MuscleGroup.CORE),
                new CategoryEntity("Upper abs", CategoryEntity.MuscleGroup.CORE),
                new CategoryEntity("Lower abs", CategoryEntity.MuscleGroup.CORE),
                new CategoryEntity("Obliques", CategoryEntity.MuscleGroup.CORE),
                new CategoryEntity("Chest", CategoryEntity.MuscleGroup.CHEST),
                new CategoryEntity("Upper chest", CategoryEntity.MuscleGroup.CHEST),
                new CategoryEntity("Lower chest", CategoryEntity.MuscleGroup.CHEST),
                new CategoryEntity("Inner chest", CategoryEntity.MuscleGroup.CHEST),
                new CategoryEntity("Outer chest", CategoryEntity.MuscleGroup.CHEST),
                new CategoryEntity("Serratus anterior", CategoryEntity.MuscleGroup.CHEST),
                new CategoryEntity("Back", CategoryEntity.MuscleGroup.BACK),
                new CategoryEntity("Upper back", CategoryEntity.MuscleGroup.BACK),
                new CategoryEntity("Lower back", CategoryEntity.MuscleGroup.BACK),
                new CategoryEntity("Lats", CategoryEntity.MuscleGroup.BACK),
                new CategoryEntity("Rhomboids", CategoryEntity.MuscleGroup.BACK),
                new CategoryEntity("Traps", CategoryEntity.MuscleGroup.BACK),
                new CategoryEntity("Biceps", CategoryEntity.MuscleGroup.ARMS),
                new CategoryEntity("Triceps", CategoryEntity.MuscleGroup.ARMS),
                new CategoryEntity("Forearms", CategoryEntity.MuscleGroup.ARMS),
                new CategoryEntity("Shoulders", CategoryEntity.MuscleGroup.SHOULDERS),
                new CategoryEntity("Anterior delts", CategoryEntity.MuscleGroup.SHOULDERS),
                new CategoryEntity("Lateral delts", CategoryEntity.MuscleGroup.SHOULDERS),
                new CategoryEntity("Posterior delts", CategoryEntity.MuscleGroup.SHOULDERS),
                new CategoryEntity("Glutes", CategoryEntity.MuscleGroup.LEGS),
                new CategoryEntity("Hip abductors", CategoryEntity.MuscleGroup.LEGS),
                new CategoryEntity("Hip adductors", CategoryEntity.MuscleGroup.LEGS),
                new CategoryEntity("Hamstrings", CategoryEntity.MuscleGroup.LEGS),
                new CategoryEntity("Quadriceps", CategoryEntity.MuscleGroup.LEGS),
                new CategoryEntity("Calves", CategoryEntity.MuscleGroup.LEGS),
                new CategoryEntity("Rotator cuff", CategoryEntity.MuscleGroup.SHOULDERS)
                ));

        for (CategoryEntity category : categories) {
            Optional<CategoryEntity> existingCategory = categoryRepository.findByName(category.getName());
            if (existingCategory.isEmpty()) {
                categoryRepository.save(category);
            }
        }
    }

    public List<CategoryEntity> findCategoriesByNames(List<String> names) {
        return names.stream()
                .map(name -> categoryRepository.findByName(name).orElseThrow(() -> new RuntimeException("Category not found: " + name)))
                .collect(Collectors.toList());
    }
}
