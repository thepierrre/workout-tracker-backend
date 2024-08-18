package com.example.gymapp.services;

import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SetupExerciseTypeService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ExerciseTypeRepository exerciseTypeRepository;

    public void createDefaultExercisesIfNonExistent() {
        Map<String, Map<ExerciseTypeEntity.Equipment, List<String>>> exercisesWithCategories = Map.ofEntries(
                Map.entry("Barbell bench press", Map.of(ExerciseTypeEntity.Equipment.BARBELL, List.of("Chest", "Anterior delts", "Triceps"))),
                Map.entry("Incline barbell bench press", Map.of(ExerciseTypeEntity.Equipment.BARBELL, List.of("Chest", "Anterior delts", "Triceps"))),
                Map.entry("Dumbbell bench press", Map.of(ExerciseTypeEntity.Equipment.DUMBBELLS, List.of("Chest", "Anterior delts", "Triceps"))),
                Map.entry("Incline dumbbell bench press", Map.of(ExerciseTypeEntity.Equipment.DUMBBELLS, List.of("Chest", "Anterior delts", "Triceps"))),
                Map.entry("Barbell squats", Map.of(ExerciseTypeEntity.Equipment.BARBELL, List.of("Glutes", "Quadriceps", "Core"))),
                Map.entry("Barbell deadlifts", Map.of(ExerciseTypeEntity.Equipment.BARBELL, List.of("Glutes", "Hamstrings", "Lower back", "Core"))),
                Map.entry("Standing calf raises", Map.of(ExerciseTypeEntity.Equipment.MACHINE, List.of("Calves"))),
                Map.entry("Seated calf raises", Map.of(ExerciseTypeEntity.Equipment.MACHINE, List.of("Calves"))),
                Map.entry("Dumbbell shoulder press", Map.of(ExerciseTypeEntity.Equipment.DUMBBELLS, List.of("Anterior delts"))),
                Map.entry("Dumbbell rear delt flies", Map.of(ExerciseTypeEntity.Equipment.DUMBBELLS, List.of("Posterior delts"))),
                Map.entry("Dumbbell shoulder lateral raise", Map.of(ExerciseTypeEntity.Equipment.DUMBBELLS, List.of("Lateral delts"))),
                Map.entry("Barbell standing rows", Map.of(ExerciseTypeEntity.Equipment.BARBELL, List.of("Lats", "Traps", "Rhomboids", "Posterior delts"))),
                Map.entry("Machine seated rows", Map.of(ExerciseTypeEntity.Equipment.MACHINE, List.of("Lats", "Traps", "Rhomboids", "Posterior delts"))),
                Map.entry("Pull-downs", Map.of(ExerciseTypeEntity.Equipment.BAR, List.of("Lats", "Traps", "Rhomboids"))),
                Map.entry("Pull-ups", Map.of(ExerciseTypeEntity.Equipment.BAR, List.of("Lats", "Traps", "Rhomboids", "Core"))),
                Map.entry("Machine chest flies", Map.of(ExerciseTypeEntity.Equipment.MACHINE, List.of("Chest"))),
                Map.entry("Dumbbell biceps curls", Map.of(ExerciseTypeEntity.Equipment.DUMBBELLS, List.of("Biceps"))),
                Map.entry("Barbell biceps curls", Map.of(ExerciseTypeEntity.Equipment.BARBELL, List.of("Biceps")))
        );

        exercisesWithCategories.forEach((exerciseName, details) -> {
            details.forEach((equipment, categoryNames) -> {
                createExerciseTypeIfNonExistent(exerciseName, categoryNames, equipment, true, null);
            });
        });
    }

    // Create several custom exercises for the example user for demonstration purposes.
    public void createCustomExercisesForExampleUserIfNonExistent(String username) {

        Map<String, Map<ExerciseTypeEntity.Equipment, List<String>>> exercisesWithCategories = Map.ofEntries(
                Map.entry("Decline barbell bench press", Map.of(ExerciseTypeEntity.Equipment.BARBELL, List.of("Upper chest", "Anterior delts", "Triceps"))),
                Map.entry("Leg press", Map.of(ExerciseTypeEntity.Equipment.MACHINE, List.of("Quadriceps", "Hamstrings", "Glutes"))),
                Map.entry("Hanging leg raises with twist", Map.of(ExerciseTypeEntity.Equipment.MACHINE, List.of("Abs", "Obliques")))
        );

        exercisesWithCategories.forEach((exerciseName, details) -> {
            details.forEach((equipment, categoryNames) -> {
                createExerciseTypeIfNonExistent(exerciseName, categoryNames, equipment, false, username);
            });
        });
    }

    private void createExerciseTypeIfNonExistent(
            String name,
            List<String> categoryNames,
            ExerciseTypeEntity.Equipment equipment,
            Boolean isDefault,
            String username
    ) {
        Optional<UserEntity> user = Optional.empty();
        if (username != null) {
            user = userRepository.findByUsername(username);
        }

        Optional<ExerciseTypeEntity> existingExerciseType = exerciseTypeRepository.findByName(name);

        if (existingExerciseType.isEmpty()) {
            List<CategoryEntity> categories = categoryService.findCategoriesByNames(categoryNames);
            ExerciseTypeEntity exerciseType = new ExerciseTypeEntity(name, categories, equipment, isDefault);

            exerciseTypeRepository.save(exerciseType);

            if (user.isPresent()) {
                exerciseType.setUser(user.get());
                user.get().getExerciseTypes().add(exerciseType);
                userRepository.save(user.get());
            }
        }
    }
}
