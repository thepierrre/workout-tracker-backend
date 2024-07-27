package com.example.gymapp.domain.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "blueprint_working_sets")
public class BlueprintWorkingSetEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "routine_exercise_id", referencedColumnName = "id")
    private RoutineExerciseEntity routineExercise;

    private Short reps;

    private double weight;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime creationTimedate;
}
