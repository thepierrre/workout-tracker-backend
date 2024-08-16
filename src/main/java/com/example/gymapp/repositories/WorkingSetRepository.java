package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.InstanceWorkingSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkingSetRepository extends JpaRepository<InstanceWorkingSetEntity, UUID> {}
