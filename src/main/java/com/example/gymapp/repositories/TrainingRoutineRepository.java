package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.TrainingRoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrainingRoutineRepository extends JpaRepository<TrainingRoutineEntity, UUID> {
}
