package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
@Entity
@Table(name = "blueprint_working_sets")
public class BlueprintWorkingSetEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "routine_exercise_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"routine", "workingSets"})
    private RoutineExerciseEntity routineExercise;

    private Short reps;

    private Double weight;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime creationTimedate;
}
