package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.ExerciseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends CrudRepository<ExerciseEntity, String> {
}
