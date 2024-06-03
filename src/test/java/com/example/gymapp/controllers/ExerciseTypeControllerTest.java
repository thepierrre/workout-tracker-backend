package com.example.gymapp.controllers;

import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.helpers.CategoryDataHelper;
import com.example.gymapp.helpers.ExerciseTypeDataHelper;
import com.example.gymapp.helpers.UserDataHelper;
import com.example.gymapp.repositories.CategoryRepository;
import com.example.gymapp.repositories.ExerciseTypeRepository;
import com.example.gymapp.repositories.RoleRepository;
import com.example.gymapp.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ExerciseTypeControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ExerciseTypeController exerciseTypeController;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ExerciseTypeRepository exerciseTypeRepository;

    @MockBean
    RoleRepository roleRepository;

    private static CategoryEntity category1;
    private static CategoryEntity category2;
    private static CategoryEntity category3;
    private static ExerciseTypeEntity exerciseType1;
    private static ExerciseTypeEntity exerciseType2;

    private static UserEntity user1;
    private static UserEntity user2;

    @BeforeAll
    static void setUpEntities() {
        CategoryDataHelper.createCategoryEntity("hamstrings");
        CategoryDataHelper.createCategoryEntity("glutes");
        CategoryDataHelper.createCategoryEntity("lower back");

        ExerciseTypeDataHelper.createExerciseTypeEntity("bench press");
        ExerciseTypeDataHelper.createExerciseTypeEntity("biceps curls");

        UserDataHelper.createUserEntity("jack890", "jack@abc.com", "p@ss");
        UserDataHelper.createUserEntity("kate123", "kate123@abc.com", "pswd");
    }

    // export to a separate class at a later point
    @BeforeEach
    void setUp() {
        when(categoryRepository.save(category1)).thenReturn(category1);
        when(categoryRepository.save(category2)).thenReturn(category2);
        when(categoryRepository.save(category3)).thenReturn(category3);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        when(exerciseTypeRepository.save(exerciseType1)).thenReturn(exerciseType1);
        when(exerciseTypeRepository.save(exerciseType2)).thenReturn(exerciseType2);
        exerciseTypeRepository.save(exerciseType1);
        exerciseTypeRepository.save(exerciseType2);

        when(userRepository.save(user1)).thenReturn(user1);
        when(userRepository.save(user2)).thenReturn(user2);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        exerciseTypeRepository.deleteAll();
    }

    @Test
    void testFindAll() {
    }

    @Test
    void testFindAllForUser() {
    }

    @Test
    void testCreateExerciseType() {

    }

    @Test
    void testDeleteById() {
    }

    @Test
    void testDeleteAll() {
    }

    @Test
    void testUpdateById() {
    }
}