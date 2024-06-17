package com.example.gymapp.services;

import com.example.gymapp.repositories.ExerciseInstanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExerciseInstanceServiceTest {

    @Autowired
    ExerciseInstanceService exerciseInstanceService;

    @Autowired
    ExerciseInstanceRepository exerciseInstanceRepository;

    @Test
    void createWorkingSetforExercise_Success() {
    }

    @Test
    void createWorkingSetforExercise_ExerciseIdNotFound() {
    }

    @Test
    void updateWorkingSetById_Success() {
    }

    @Test
    void updateWorkingSetById_ExerciseIdNotFound() {
    }

    @Test
    void updateWorkingSetById_SetIdNotFound() {
    }

    @Test
    void deleteWorkingSetById_ExerciseIdNotFound() {
    }

    @Test
    void deleteWorkingSetById_SetIdNotFound() {
    }

    @Test
    void deleteExerciseById_Success() {
    }

    @Test
    void deleteExerciseById_ExerciseIdNotFound() {
    }
}