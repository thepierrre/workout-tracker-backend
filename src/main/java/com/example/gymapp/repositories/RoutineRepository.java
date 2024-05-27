package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoutineRepository extends JpaRepository<RoutineEntity, UUID> {

    Optional<RoutineEntity> findByName(String name);
}
