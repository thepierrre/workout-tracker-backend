package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.CategoryEntity;
import com.example.gymapp.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    boolean existsByName(String name);
    Optional<CategoryEntity> findByName(String name);
}
