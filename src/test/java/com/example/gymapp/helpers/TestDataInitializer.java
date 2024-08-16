package com.example.gymapp.helpers;

import com.example.gymapp.domain.dto.*;
import com.example.gymapp.domain.entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
        public ExerciseTypeEntity exerciseTypeEntity4;
        public ExerciseTypeEntity exerciseTypeEntity5;
        public ExerciseTypeDto exerciseTypeRequestDto1;
        public ExerciseTypeDto exerciseTypeRequestDto2;
        public ExerciseTypeDto exerciseTypeRequestDto3;
        public ExerciseTypeDto exerciseTypeRequestDto4;
        public ExerciseTypeDto exerciseTypeRequestDto5;
        public ExerciseTypeDto exerciseTypeResponseDto1;
        public ExerciseTypeDto exerciseTypeResponseDto2;
        public ExerciseTypeDto exerciseTypeResponseDto3;
        public ExerciseTypeDto exerciseTypeResponseDto4;
        public ExerciseTypeDto exerciseTypeResponseDto5;
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
        public InstanceWorkingSetEntity instanceWorkingSetEntity1;
        public InstanceWorkingSetEntity instanceWorkingSetEntity2;
        public InstanceWorkingSetEntity instanceWorkingSetEntity3;
        public InstanceWorkingSetDto workingSetRequestDto1;
        public InstanceWorkingSetDto workingSetRequestDto2;
        public InstanceWorkingSetDto workingSetRequestDto3;
        public InstanceWorkingSetDto workingSetResponseDto1;
        public InstanceWorkingSetDto workingSetResponseDto2;
        public InstanceWorkingSetDto workingSetResponseDto3;
        public LoginDto loginDto1;
        public RegisterDto registerDto1;

        public RoutineExerciseEntity routineExerciseEntity1;
        public RoutineExerciseEntity routineExerciseEntity2;
        public RoutineExerciseEntity routineExerciseEntity3;
        public RoutineExerciseDto routineExerciseRequestDto1;
        public RoutineExerciseDto routineExerciseRequestDto2;
        public RoutineExerciseDto routineExerciseRequestDto3;
        public RoutineExerciseDto routineExerciseResponseDto1;
        public RoutineExerciseDto routineExerciseResponseDto2;
        public RoutineExerciseDto routineExerciseResponseDto3;
        public UserSettingsEntity userSettingsEntity1;
        public UserSettingsEntity userSettingsEntity2;
        public UserSettingsEntity userSettingsEntity3;
        public UserSettingsEntity userSettingsEntity4;
        public UserSettingsDto userSettingsRequestDto1;
        public UserSettingsDto userSettingsRequestDto2;
        public UserSettingsDto userSettingsRequestDto3;
        public UserSettingsDto userSettingsRequestDto4;
        public UserSettingsDto userSettingsResponseDto1;
        public UserSettingsDto userSettingsResponseDto2;
        public UserSettingsDto userSettingsResponseDto3;
        public UserSettingsDto userSettingsResponseDto4;

    }

    public static TestData initializeTestData() {
        TestData testData = new TestData();

        testData.user1 = UserDataHelper.createUserEntity("user1", "user1@example.com", "encoded1");
        testData.user2 = UserDataHelper.createUserEntity("user2", "user2@example.com", "encoded2");
        testData.userDto1 = UserDataHelper.createUserResponseDto("user1", "user1@example.com", "pass1");
        testData.userDto2 = UserDataHelper.createUserResponseDto("user2", "user2@example.com", "pass2");
        testData.categoryEntity1 = CategoryDataHelper.createCategoryEntity("category1");
        testData.categoryEntity2 = CategoryDataHelper.createCategoryEntity("category2");
        testData.categoryEntity3 = CategoryDataHelper.createCategoryEntity("category3");
        testData.categoryRequestDto1 = CategoryDataHelper.createCategoryRequestDto("category1");
        testData.categoryRequestDto2 = CategoryDataHelper.createCategoryRequestDto("category2");
        testData.categoryRequestDto3 = CategoryDataHelper.createCategoryRequestDto("category3");
        testData.categoryResponseDto1 = CategoryDataHelper.createCategoryResponseDto("category1");
        testData.categoryResponseDto2 = CategoryDataHelper.createCategoryResponseDto("category2");
        testData.categoryResponseDto3 = CategoryDataHelper.createCategoryResponseDto("category3");
        testData.exerciseTypeEntity1 = ExerciseTypeDataHelper.createExerciseTypeEntity("exercise1", false);
        testData.exerciseTypeEntity2 = ExerciseTypeDataHelper.createExerciseTypeEntity("exercise2", false);
        testData.exerciseTypeEntity3 = ExerciseTypeDataHelper.createExerciseTypeEntity("exercise3", false);
        testData.exerciseTypeEntity4 = ExerciseTypeDataHelper.createExerciseTypeEntity("exercise4", true);
        testData.exerciseTypeEntity5 = ExerciseTypeDataHelper.createExerciseTypeEntity("exercise5", true);
        testData.exerciseTypeRequestDto1 = ExerciseTypeDataHelper.createExerciseTypeRequestDto("exercise1");
        testData.exerciseTypeRequestDto2 = ExerciseTypeDataHelper.createExerciseTypeRequestDto("exercise2");
        testData.exerciseTypeRequestDto3 = ExerciseTypeDataHelper.createExerciseTypeRequestDto("exercise3");
        testData.exerciseTypeRequestDto4 = ExerciseTypeDataHelper.createExerciseTypeRequestDto("exercise4");
        testData.exerciseTypeRequestDto5 = ExerciseTypeDataHelper.createExerciseTypeRequestDto("exercise5");
        testData.exerciseTypeResponseDto1 = ExerciseTypeDataHelper.createExerciseTypeResponseDto("exercise1", false);
        testData.exerciseTypeResponseDto2 = ExerciseTypeDataHelper.createExerciseTypeResponseDto("exercise2", false);
        testData.exerciseTypeResponseDto3 = ExerciseTypeDataHelper.createExerciseTypeResponseDto("exercise3", false);
        testData.exerciseTypeResponseDto4 = ExerciseTypeDataHelper.createExerciseTypeResponseDto("exercise4", true);
        testData.exerciseTypeResponseDto5 = ExerciseTypeDataHelper.createExerciseTypeResponseDto("exercise5", true);
        testData.exerciseInstanceEntity1 = ExerciseInstanceDataHelper.createExerciseInstanceEntity("exercise1");
        testData.exerciseInstanceEntity2 = ExerciseInstanceDataHelper.createExerciseInstanceEntity("exercise2");
        testData.exerciseInstanceEntity3 = ExerciseInstanceDataHelper.createExerciseInstanceEntity("exercise3");
        testData.exerciseInstanceRequestDto1 = ExerciseInstanceDataHelper.createExerciseInstanceRequestDto("exercise1");
        testData.exerciseInstanceRequestDto2 = ExerciseInstanceDataHelper.createExerciseInstanceRequestDto("exercise2");
        testData.exerciseInstanceRequestDto3 = ExerciseInstanceDataHelper.createExerciseInstanceRequestDto("exercise3");
        testData.exerciseInstanceResponseDto1 = ExerciseInstanceDataHelper.createExerciseInstanceResponseDto("exercise1");
        testData.exerciseInstanceResponseDto2 = ExerciseInstanceDataHelper.createExerciseInstanceResponseDto("exercise2");
        testData.exerciseInstanceResponseDto3 = ExerciseInstanceDataHelper.createExerciseInstanceResponseDto("exercise3");
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
        testData.instanceWorkingSetEntity1 = WorkingSetDataHelper.createWorkingSetEntity((short) 10, (short) 50, LocalDateTime.of(2023, 7, 14, 10, 30).truncatedTo(ChronoUnit.MILLIS));
        testData.instanceWorkingSetEntity2 = WorkingSetDataHelper.createWorkingSetEntity((short) 9, (short) 40, LocalDateTime.of(2023, 7, 14, 10, 30).truncatedTo(ChronoUnit.MILLIS));
        testData.instanceWorkingSetEntity3 = WorkingSetDataHelper.createWorkingSetEntity((short) 8, (short) 30, LocalDateTime.of(2023, 7, 14, 10, 30).truncatedTo(ChronoUnit.MILLIS));
        testData.workingSetResponseDto1 = WorkingSetDataHelper.createWorkingSetResponseDto((short) 10, (short) 50, LocalDateTime.of(2023, 7, 14, 10, 30).truncatedTo(ChronoUnit.MILLIS));
        testData.workingSetResponseDto2 = WorkingSetDataHelper.createWorkingSetResponseDto((short) 9, (short) 40, LocalDateTime.of(2023, 7, 14, 10, 30).truncatedTo(ChronoUnit.MILLIS));
        testData.workingSetResponseDto3 = WorkingSetDataHelper.createWorkingSetResponseDto((short) 8, (short) 30, LocalDateTime.of(2023, 7, 14, 10, 30).truncatedTo(ChronoUnit.MILLIS));
        testData.workingSetRequestDto1 = WorkingSetDataHelper.createWorkingSetRequestDto((short) 10, (short) 50);
        testData.workingSetRequestDto2 = WorkingSetDataHelper.createWorkingSetRequestDto((short) 9, (short) 40);
        testData.workingSetRequestDto3 = WorkingSetDataHelper.createWorkingSetRequestDto((short) 8, (short) 30);
        testData.loginDto1 = AuthDataHelper.createLoginDto("user1", "pass1");
        testData.registerDto1 = AuthDataHelper.createRegisterDto("user1", "user1@example.com", "pass1");
        testData.routineExerciseEntity1 = RoutineExerciseDataHelper.createRoutineExerciseEntity("exercise1");
        testData.routineExerciseEntity2 = RoutineExerciseDataHelper.createRoutineExerciseEntity("exercise2");
        testData.routineExerciseEntity3 = RoutineExerciseDataHelper.createRoutineExerciseEntity("exercise3");
        testData.routineExerciseRequestDto1 = RoutineExerciseDataHelper.createRoutineExerciseRequestDto("exercise1");
        testData.routineExerciseRequestDto2 = RoutineExerciseDataHelper.createRoutineExerciseRequestDto("exercise2");
        testData.routineExerciseRequestDto3 = RoutineExerciseDataHelper.createRoutineExerciseRequestDto("exercise3");
        testData.routineExerciseResponseDto1 = RoutineExerciseDataHelper.createRoutineExerciseResponseDto("exercise1");
        testData.routineExerciseResponseDto2 = RoutineExerciseDataHelper.createRoutineExerciseResponseDto("exercise2");
        testData.routineExerciseResponseDto3 = RoutineExerciseDataHelper.createRoutineExerciseResponseDto("exercise3");
        testData.userSettingsEntity1 = UserSettingsDataHelper.createUserSettingsEntity(1.0, "kgs");
        testData.userSettingsEntity2 = UserSettingsDataHelper.createUserSettingsEntity(10.0, "kgs");
        testData.userSettingsEntity3 = UserSettingsDataHelper.createUserSettingsEntity(5.0, "lbs");
        testData.userSettingsEntity4 = UserSettingsDataHelper.createUserSettingsEntity(50.0, "lbs");
        testData.userSettingsRequestDto1 = UserSettingsDataHelper.createUserSettingsRequestDto(1.0, "kgs");
        testData.userSettingsRequestDto2 = UserSettingsDataHelper.createUserSettingsRequestDto(10.0, "kgs");
        testData.userSettingsRequestDto3 = UserSettingsDataHelper.createUserSettingsRequestDto(5.0, "lbs");
        testData.userSettingsRequestDto4 = UserSettingsDataHelper.createUserSettingsRequestDto(50.0, "lbs");
        testData.userSettingsResponseDto1 = UserSettingsDataHelper.createUserSettingsResponseDto(1.0, "kgs");
        testData.userSettingsResponseDto2 = UserSettingsDataHelper.createUserSettingsResponseDto(10.0, "kgs");
        testData.userSettingsResponseDto3 = UserSettingsDataHelper.createUserSettingsResponseDto(5.0, "lbs");
        testData.userSettingsResponseDto4 = UserSettingsDataHelper.createUserSettingsResponseDto(50.0, "lbs");

        return testData;

    }




}
