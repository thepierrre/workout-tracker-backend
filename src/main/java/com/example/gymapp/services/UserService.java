package com.example.gymapp.services;
import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.example.gymapp.domain.dto.RoutineDto;
import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.dto.WorkoutDto;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.Role;
import com.example.gymapp.domain.entities.RoutineEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.impl.ExerciseTypeMapper;
import com.example.gymapp.mappers.impl.RoutineMapper;
import com.example.gymapp.mappers.impl.UserMapper;
import com.example.gymapp.mappers.impl.WorkoutMapper;
import com.example.gymapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoutineMapper routineMapper;

    @Autowired
    private ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    private WorkoutMapper workoutMapper;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapToDto).toList();
    }

    public Optional<UserEntity> findById(UUID id) {
        return userRepository.findById(id);
    }

    public void deleteById(UUID id) {
//        UserEntity user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException(String.format(
//                        "User with the username \"%s\" not found.", username)));

//        for (ExerciseTypeEntity exerciseType : category.getExerciseTypes()) {
//            exerciseType.getCategories().remove(category);
//            exerciseTypeRepository.save(exerciseType);
//        }


        userRepository.deleteById(id);
    }

    public void deleteAll() {

        userRepository.deleteAll();
    }

    public boolean isExists(UUID id) {
        return userRepository.existsById(id);
    }


    public List<RoutineDto> getTrainingRoutinesForUser(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        return userEntity.get().getRoutines().stream().map(routineMapper::mapToDto).toList();
    }

    public List<ExerciseTypeDto> getExerciseTypesForUser(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userEntity.get().getExerciseTypes().stream().map(exerciseTypeMapper::mapToDto).toList();
    }

    public List<WorkoutDto> getWorkoutsForUser(UUID id) {
        return null;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> NotFoundHandler(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> BadRequestHandler(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(Map.of("message", e.getMessage()));
    }


    public Optional<UserEntity> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
