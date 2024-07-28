package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.ExerciseTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExerciseTypeRepository extends JpaRepository<ExerciseTypeEntity, UUID> {
    Optional<ExerciseTypeEntity> findByUserAndName(UserEntity user, String name);
    Optional<ExerciseTypeEntity> findByName(String name);
    Optional<List<ExerciseTypeEntity>> findByUserUsername(String username);

    @Query("SELECT ex FROM ExerciseTypeEntity ex WHERE ex.isDefault = true")
    List<ExerciseTypeEntity> findAllDefault();
}
