package com.example.gymapp.config;

import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.services.AuthService;
import com.example.gymapp.services.CategoryService;
import com.example.gymapp.services.ExerciseTypeService;
import com.example.gymapp.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
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
    private ExerciseTypeService exerciseTypeService;

    @Autowired
    private AuthService authService;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onApplicationReady() {
        roleService.createRoleIfNotExistent("USER");
        categoryService.createCategoriesIfNotExistent();
        // Create a test user for demonstration purposes.
        authService.createUserIfNotExistent(new RegisterDto(
                "test",
                "test@example.com",
                "test"
        ));
        exerciseTypeService.createDefaultExercisesIfNotExistent();
    }

}
