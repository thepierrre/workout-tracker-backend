package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.WorkingSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkingSetRepository extends JpaRepository<WorkingSetEntity, Long> {
}
