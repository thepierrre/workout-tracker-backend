package com.example.gymapp.services;

import com.example.gymapp.domain.entities.UserEntity;
import java.util.List;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    List<UserEntity> findAll();
}
