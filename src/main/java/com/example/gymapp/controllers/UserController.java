package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.UserResponseDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoutineMapper routineMapper;

    @Autowired
    WorkoutMapper workoutMapper;

    @Autowired
    ExerciseTypeMapper exerciseTypeMapper;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<UserEntity> user = userService.findByUsername(userDetails.getUsername());

            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUsername(user.get().getUsername());
            userResponseDto.setEmail(user.get().getEmail());
            userResponseDto.setChangeThreshold(user.get().getChangeThreshold());
            userResponseDto.setRoutines(user.get().getRoutines().stream()
                    .map(routineMapper::mapToDto).toList());
            userResponseDto.setWorkouts(user.get().getWorkouts().stream()
                    .map(workoutMapper::mapToDto).toList());
            userResponseDto.setExerciseTypes(user.get().getExerciseTypes().stream()
                    .map(exerciseTypeMapper::mapToDto).toList());
            return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/threshold")
    public Short editChangeThreshold(@AuthenticationPrincipal UserDetails userDetails, Short num) {
        Optional<UserEntity> user = userService.findByUsername(userDetails.getUsername());
        user.get().setChangeThreshold(num);
        return user.get().getChangeThreshold();
    }
}




