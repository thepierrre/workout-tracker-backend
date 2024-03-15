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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity deleteById(@PathVariable("id") UUID id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/users")
    public ResponseEntity deleteAll() {
        userService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") UUID id, @RequestBody UserDto userDto) {
        if (!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.update(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);
    }
}
