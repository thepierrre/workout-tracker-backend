package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findById(UserEntity userEntity);
}
