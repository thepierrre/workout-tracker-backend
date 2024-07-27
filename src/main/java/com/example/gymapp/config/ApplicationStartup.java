package com.example.gymapp.config;

import com.example.gymapp.domain.dto.RegisterDto;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.services.AuthService;
import com.example.gymapp.services.CategoryService;
import com.example.gymapp.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApplicationStartup {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onApplicationReady() {
        roleService.createRoleIfNotExistent("USER");
        categoryService.createCategoriesIfNotExistent();

        if (userRepository.findByUsername("test").isEmpty()) {
            RegisterDto testRegisterDto = new RegisterDto(
                    "test",
                    "test@example.com",
                    "test");
            authService.register(testRegisterDto);
        }
    }
}
