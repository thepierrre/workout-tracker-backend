package com.example.gymapp.services;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.*;
import com.example.gymapp.helpers.TestDataInitializer;
import com.example.gymapp.helpers.UserDataHelper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.UserRepository;
import com.example.gymapp.repositories.WorkoutRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WorkoutServiceTest {

    @Autowired
    WorkoutService workoutService;

    @MockBean
    WorkoutRepository workoutRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    WorkoutMapper workoutMapper;



    @BeforeEach
    void setUp() {
        List<ExerciseTypeEntity> exercises = new ArrayList<>();
        List<RoutineEntity> routines = new ArrayList<>();
        List<WorkoutEntity> workouts = new ArrayList<>();
        List<CategoryEntity> categories = new ArrayList<>();



//        TestDataInitializer.TestData.user1 = UserDataHelper.createUserEntity(
//                "user1",
//                "user1@example.com",
//                "pass1",
//                exercises,
//                routines,
//                workouts
//        );
//
//        user2 = UserDataHelper.createUserEntity(
//                "user2",
//                "user2@example.com",
//                "pass2",
//                exercises,
//                routines,
//                workouts
//        );
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testFindAllForUser() {
    }

    @Test
    void testFindById() {
    }

    @Test
    void testCreateWorkout_Success() {
    }

    @Test
    void testDeleteById() {
    }
}