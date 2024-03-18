package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.WorkingSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingSetRepository extends JpaRepository<WorkingSetEntity, Short> {
}
