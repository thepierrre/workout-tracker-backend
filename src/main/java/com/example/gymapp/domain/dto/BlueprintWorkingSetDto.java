package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.RoutineExerciseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlueprintWorkingSetDto {

    private UUID id;

    @JsonIgnoreProperties({"routine", "workingSets"})
    private RoutineExerciseEntity routineExercise;

    private Short reps;

    private Double weight;

    private LocalDateTime creationTimedate;

    public BlueprintWorkingSetDto(String id) {
        this.id = UUID.fromString(id);
    }
}
