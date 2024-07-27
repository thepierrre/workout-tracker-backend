package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.RoutineExerciseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlueprintWorkingSetDto {

    private UUID id;

    private RoutineExerciseEntity routineExercise;

    private Short reps;

    private double weight;

    private LocalDateTime creationTimedate;

    public BlueprintWorkingSetDto(String id) {
        this.id = UUID.fromString(id);
    }
}
