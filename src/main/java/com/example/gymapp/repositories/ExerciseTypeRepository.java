package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExerciseTypeRepository extends JpaRepository<ExerciseTypeEntity, UUID> {

    List<ExerciseTypeEntity> findAllByCategoriesContaining(CategoryEntity categoryEntity);

    Optional<ExerciseTypeEntity> findByUserAndName(UserEntity user, String name);

    Optional<List<ExerciseTypeEntity>> findByUserUsername(String username);

}
