package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.UserResponseDto;
import com.example.gymapp.domain.dto.UserSettingsDto;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            userResponseDto.setRoutines(user.get().getRoutines().stream()
                    .map(routineMapper::mapToDto).toList());
            userResponseDto.setWorkouts(user.get().getWorkouts().stream()
                    .map(workoutMapper::mapToDto).toList());
            userResponseDto.setExerciseTypes(user.get().getExerciseTypes().stream()
                    .map(exerciseTypeMapper::mapToDto).toList());
            return ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("user-settings")
    public ResponseEntity<UserSettingsDto> updateUserSettings(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UserSettingsDto userSettingsDto) {
        UserSettingsDto userSettingsDtoResponse = userService.updateUserSettings(userDetails.getUsername(), userSettingsDto);
        return new ResponseEntity<>(userSettingsDtoResponse, HttpStatus.OK);
    }

    @GetMapping("user-settings")
    public ResponseEntity<UserSettingsDto> getUserSettings(@AuthenticationPrincipal UserDetails userDetails) {
        UserSettingsDto userSettingsDto = userService.getUserSettings(userDetails.getUsername());
        return new ResponseEntity<>(userSettingsDto, HttpStatus.OK);
    }
}




