package com.example.gymapp.services;

import com.example.gymapp.domain.dto.BlueprintWorkingSetDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.RoutineExerciseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SetupRoutineService {

    @Autowired
    RoutineService routineService;

    public void createRoutinesForExampleUserIfNonExistent(String username) {
        BlueprintWorkingSetDto workingSet1 = new BlueprintWorkingSetDto((short) 10, 12.0);
        BlueprintWorkingSetDto workingSet2 = new BlueprintWorkingSetDto((short) 11, 40.0);
        BlueprintWorkingSetDto workingSet3 = new BlueprintWorkingSetDto((short) 11, 60.0);
        BlueprintWorkingSetDto workingSet4 = new BlueprintWorkingSetDto((short) 10, 45.0);
        BlueprintWorkingSetDto workingSet5 = new BlueprintWorkingSetDto((short) 10, 25.0);

        RoutineExerciseDto exercise1 = new RoutineExerciseDto(
                "Barbell bench press",
                Collections.nCopies(4, workingSet4)
        );
        RoutineExerciseDto exercise2 = new RoutineExerciseDto(
                "Barbell squats",
                Collections.nCopies(3, workingSet3)
        );
        RoutineExerciseDto exercise3 = new RoutineExerciseDto(
                "Pull-ups",
                Collections.nCopies(4, workingSet2)
        );
        RoutineExerciseDto exercise4 = new RoutineExerciseDto(
                "Dumbbell biceps curls",
                Collections.nCopies(4, workingSet5)
        );
        RoutineExerciseDto exercise5 = new RoutineExerciseDto(
                "Barbell deadlifts",
                Collections.nCopies(4, workingSet3)
        );
        RoutineExerciseDto exercise6 = new RoutineExerciseDto(
                "Leg press",
                Collections.nCopies(4, workingSet4)
        );
        RoutineExerciseDto exercise7 = new RoutineExerciseDto(
                "Dumbbell shoulder press",
                Collections.nCopies(3, workingSet1)
        );
        RoutineExerciseDto exercise8 = new RoutineExerciseDto(
                "Standing calf raises",
                Collections.nCopies(3, workingSet1)
        );

        RoutineDto routine1 = new RoutineDto(
                "Full Body Workout A",
                List.of(exercise1, exercise2, exercise3, exercise4));

        RoutineDto routine2 = new RoutineDto(
                "Full Body Workout B",
                List.of(exercise5, exercise6, exercise7, exercise8));

        routineService.createRoutine(routine1, username);
        routineService.createRoutine(routine2, username);
    }
}
