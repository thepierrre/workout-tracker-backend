package com.example.gymapp.domain.entities;

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
@Table(name = "instance_working_sets")
public class InstanceWorkingSetEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "exercise_instance_id", referencedColumnName = "id")
    private ExerciseInstanceEntity exerciseInstance;

    private Short reps;

    private Double weight;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP(3)")
    private LocalDateTime creationTimedate;
}

