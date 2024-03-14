package com.example.gymapp.controllers;

import com.example.gymapp.domain.dto.UserDto;
import com.example.gymapp.domain.entities.ExerciseEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.example.gymapp.mappers.Mapper;
import com.example.gymapp.mappers.impl.UserMapper;
import com.example.gymapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @GetMapping(path = "/users")
    public List<UserEntity> getAll() {
        return userService.findAll();
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity createdUser = userService.createUser(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(userEntity), HttpStatus.CREATED);
    }
}
