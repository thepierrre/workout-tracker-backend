package com.example.gymapp.config;

import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("!test")
@Component
public class ApplicationStartup {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetupExerciseTypeService setupExerciseTypeService;

    @Autowired
    private SetupRoutineService setupRoutineService;

    @Autowired
    private AuthService authService;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onApplicationReady() {
        roleService.createRoleIfNotExistent("USER");
        categoryService.createCategoriesIfNotExistent();
        // Create an example user for demonstration.
        authService.createUserIfNotExistent(new RegisterDto(
                "CardioManiac",
                "cardiomaniac@gymmail.com",
                "123"
        ));
        // Create default exercises to be available for every new user.
        setupExerciseTypeService.createDefaultExercisesIfNonExistent();
        // Create custom exercises for the example user for demonstration.
        setupExerciseTypeService.createCustomExercisesForExampleUserIfNonExistent("CardioManiac");
        // Create training routines for the example user for demonstration.
        setupRoutineService.createRoutinesForExampleUserIfNonExistent("CardioManiac");
    }
}
