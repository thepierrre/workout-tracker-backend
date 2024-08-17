package com.example.gymapp.repositories;

import com.example.gymapp.domain.entities.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {
    Optional<List<WorkoutEntity>> findByUserUsername(String username);
    //List<WorkoutEntity> findByUserUsernameAndCreationDate(String username, LocalDate date);
    @Query("SELECT w FROM WorkoutEntity w WHERE w.user.username = :username AND w.creationDate = :date")
    List<WorkoutEntity> findByUserUsernameAndCreationDate(@Param("username") String username, @Param("date") LocalDate date);
}
