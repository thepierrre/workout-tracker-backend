package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingRoutineRepository extends JpaRepository<TrainingRoutineEntity, UUID> {

    boolean existsByName(String name);
    Optional<TrainingRoutineEntity> findByName(String name);
}
