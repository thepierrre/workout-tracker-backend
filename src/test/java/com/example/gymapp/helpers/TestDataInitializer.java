package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.CategoryDto;
import com.example.gymapp.domain.dto.ExerciseInstanceDto;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.*;

public class TestDataInitializer {

    public static class TestData {

        public UserEntity user1;
        public UserEntity user2;
        public CategoryEntity categoryEntity1;
        public CategoryEntity categoryEntity2;
        public CategoryEntity categoryEntity3;
        public CategoryDto categoryRequestDto1;
        public CategoryDto categoryRequestDto2;
        public CategoryDto categoryRequestDto3;
        public CategoryDto categoryResponseDto1;
        public CategoryDto categoryResponseDto2;
        public CategoryDto categoryResponseDto3;

        public ExerciseTypeEntity exerciseTypeEntity1;
        public ExerciseTypeEntity exerciseTypeEntity2;
        public ExerciseTypeEntity exerciseTypeEntity3;
        public ExerciseTypeDto exerciseTypeRequestDto1;
        public ExerciseTypeDto exerciseTypeRequestDto2;
        public ExerciseTypeDto exerciseTypeRequestDto3;
        public ExerciseTypeDto exerciseTypeResponseDto1;
        public ExerciseTypeDto exerciseTypeResponseDto2;
        public ExerciseTypeDto exerciseTypeResponseDto3;
        public ExerciseInstanceEntity exerciseInstanceEntity1;
        public ExerciseInstanceEntity exerciseInstanceEntity2;
        public ExerciseInstanceEntity exerciseInstanceEntity3;
        public ExerciseInstanceDto exerciseInstanceRequestDto1;
        public ExerciseInstanceDto exerciseInstanceRequestDto2;
        public ExerciseInstanceDto exerciseInstanceRequestDto3;
        public ExerciseInstanceDto exerciseInstanceResponseDto1;
        public ExerciseInstanceDto exerciseInstanceResponseDto2;
        public ExerciseInstanceDto exerciseInstanceResponseDto3;
        public WorkoutEntity workoutEntity1;
        public WorkoutEntity workoutEntity2;
        public WorkoutEntity workoutEntity3;
        public WorkoutDto workoutRequestDto1;
        public WorkoutDto workoutRequestDto2;
        public WorkoutDto workoutRequestDto3;
        public WorkoutDto workoutResponseDto1;
        public WorkoutDto workoutResponseDto2;
        public WorkoutDto workoutResponseDto3;

    }

    public static TestData initializeTestData() {
        TestData testData = new TestData();

        testData.user1 = UserDataHelper.createUserEntity("user1", "user1@example.com", "pass1");
        testData.user2 = UserDataHelper.createUserEntity("user2", "user2@example.com", "pass2");

        return testData;

    }




}
