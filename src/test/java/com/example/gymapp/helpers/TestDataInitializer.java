package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.*;
import com.example.gymapp.domain.entities.*;

import java.time.LocalDate;

public class TestDataInitializer {

    public static class TestData {

        public UserEntity user1;
        public UserEntity user2;

        public UserDto userDto1;
        public UserDto userDto2;
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
        public RoutineEntity routineEntity1;
        public RoutineEntity routineEntity2;
        public RoutineEntity routineEntity3;
        public RoutineDto routineRequestDto1;
        public RoutineDto routineRequestDto2;
        public RoutineDto routineRequestDto3;
        public RoutineDto routineResponseDto1;
        public RoutineDto routineResponseDto2;
        public RoutineDto routineResponseDto3;

        public WorkoutEntity workoutEntity1;
        public WorkoutEntity workoutEntity2;
        public WorkoutEntity workoutEntity3;
        public WorkoutDto workoutRequestDto1;
        public WorkoutDto workoutRequestDto2;
        public WorkoutDto workoutRequestDto3;
        public WorkoutDto workoutRequestDto4;
        public WorkoutDto workoutResponseDto1;
        public WorkoutDto workoutResponseDto2;
        public WorkoutDto workoutResponseDto3;
        public WorkingSetEntity workingSetEntity1;
        public WorkingSetEntity workingSetEntity2;
        public WorkingSetEntity workingSetEntity3;
        public WorkingSetDto workingSetResponseDto1;
        public WorkingSetDto workingSetResponseDto2;
        public WorkingSetDto workingSetResponseDto3;

    }

    public static TestData initializeTestData() {
        TestData testData = new TestData();

        testData.user1 = UserDataHelper.createUserEntity("user1", "user1@example.com", "pass1");
        testData.user2 = UserDataHelper.createUserEntity("user2", "user2@example.com", "pass2");
        testData.userDto1 = UserDataHelper.createUserResponseDto("user1", "user1@example.com", "pass1");
        testData.userDto2 = UserDataHelper.createUserResponseDto("user2", "user2@example.com", "pass2");
        testData.categoryEntity1 = CategoryDataHelper.createCategoryEntity("category1");
        testData.categoryEntity2 = CategoryDataHelper.createCategoryEntity("category2");
        testData.categoryEntity3 = CategoryDataHelper.createCategoryEntity("category3");
        testData.categoryRequestDto1 = CategoryDataHelper.createCategoryRequestDto("category1");
        testData.categoryRequestDto2 = CategoryDataHelper.createCategoryRequestDto("category2");
        testData.categoryRequestDto3 = CategoryDataHelper.createCategoryRequestDto("category3");
        testData.categoryResponseDto1 = CategoryDataHelper.createCategoryResponseDto("category1");
        testData.categoryResponseDto2 = CategoryDataHelper.createCategoryResponseDto("category1");
        testData.categoryResponseDto3 = CategoryDataHelper.createCategoryResponseDto("category1");
        testData.exerciseTypeEntity1 = ExerciseTypeDataHelper.createExerciseTypeEntity("exerciseType1");
        testData.exerciseTypeEntity2 = ExerciseTypeDataHelper.createExerciseTypeEntity("exerciseType2");
        testData.exerciseTypeEntity3 = ExerciseTypeDataHelper.createExerciseTypeEntity("exerciseType3");
        testData.exerciseTypeRequestDto1 = ExerciseTypeDataHelper.createExerciseTypeRequestDto("exerciseType1");
        testData.exerciseTypeRequestDto2 = ExerciseTypeDataHelper.createExerciseTypeRequestDto("exerciseType2");
        testData.exerciseTypeRequestDto3 = ExerciseTypeDataHelper.createExerciseTypeRequestDto("exerciseType3");
        testData.exerciseTypeResponseDto1 = ExerciseTypeDataHelper.createExerciseTypeResponseDto("exerciseType1");
        testData.exerciseTypeResponseDto2 = ExerciseTypeDataHelper.createExerciseTypeResponseDto("exerciseType2");
        testData.exerciseTypeResponseDto3 = ExerciseTypeDataHelper.createExerciseTypeResponseDto("exerciseType3");
        testData.exerciseInstanceEntity1 = ExerciseInstanceDataHelper.createExerciseInstanceEntity("exerciseType1");
        testData.exerciseInstanceEntity2 = ExerciseInstanceDataHelper.createExerciseInstanceEntity("exerciseType2");
        testData.exerciseInstanceEntity3 = ExerciseInstanceDataHelper.createExerciseInstanceEntity("exerciseType3");
        testData.exerciseInstanceRequestDto1 = ExerciseInstanceDataHelper.createExerciseInstanceRequestDto("exerciseType1");
        testData.exerciseInstanceRequestDto2 = ExerciseInstanceDataHelper.createExerciseInstanceRequestDto("exerciseType2");
        testData.exerciseInstanceRequestDto3 = ExerciseInstanceDataHelper.createExerciseInstanceRequestDto("exerciseType3");
        testData.exerciseInstanceResponseDto1 = ExerciseInstanceDataHelper.createExerciseInstanceResponseDto("exerciseType1");
        testData.exerciseInstanceResponseDto2 = ExerciseInstanceDataHelper.createExerciseInstanceResponseDto("exerciseType2");
        testData.exerciseInstanceResponseDto3 = ExerciseInstanceDataHelper.createExerciseInstanceResponseDto("exerciseType3");
        testData.routineEntity1 = RoutineDataHelper.createRoutineEntity("routine1");
        testData.routineEntity2 = RoutineDataHelper.createRoutineEntity("routine2");
        testData.routineEntity3 = RoutineDataHelper.createRoutineEntity("routine3");
        testData.routineRequestDto1 = RoutineDataHelper.createRoutineRequestDto("routine1");
        testData.routineRequestDto2 = RoutineDataHelper.createRoutineRequestDto("routine2");
        testData.routineRequestDto3 = RoutineDataHelper.createRoutineRequestDto("routine3");
        testData.routineResponseDto1 = RoutineDataHelper.createRoutineResponseDto("routine1");
        testData.routineResponseDto2 = RoutineDataHelper.createRoutineResponseDto("routine2");
        testData.routineResponseDto3 = RoutineDataHelper.createRoutineResponseDto("routine3");
        testData.workoutEntity1 = WorkoutDataHelper.createWorkoutEntity(LocalDate.of(2024, 4, 30), "routine1");
        testData.workoutEntity2 = WorkoutDataHelper.createWorkoutEntity(LocalDate.of(2024, 6, 15), "routine2");
        testData.workoutEntity3 = WorkoutDataHelper.createWorkoutEntity(LocalDate.of(2024, 6, 15), "routine3");
        testData.workoutRequestDto1 = WorkoutDataHelper.createWorkoutRequestDto(LocalDate.of(2024, 4, 30), "routine1");
        testData.workoutRequestDto2 = WorkoutDataHelper.createWorkoutRequestDto(LocalDate.of(2024, 6, 15), "routine2");
        testData.workoutRequestDto3 = WorkoutDataHelper.createWorkoutRequestDto(LocalDate.of(2024, 6, 15), "routine3");
        testData.workoutRequestDto4 = WorkoutDataHelper.createWorkoutRequestDto(LocalDate.of(2024, 6, 15), "routine4");
        testData.workoutResponseDto1 = WorkoutDataHelper.createWorkoutResponseDto(LocalDate.of(2024, 4, 30), "routine1");
        testData.workoutResponseDto2 = WorkoutDataHelper.createWorkoutResponseDto(LocalDate.of(2024, 6, 15), "routine2");
        testData.workoutResponseDto3 = WorkoutDataHelper.createWorkoutResponseDto(LocalDate.of(2024, 6, 15), "routine3");

        return testData;

    }




}
