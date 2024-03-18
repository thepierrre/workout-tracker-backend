package com.example.gymapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sets")
public class SetEntity {

    @Id
    @GeneratedValue
    private Short id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_entity_id", referencedColumnName = "id")
    private ExerciseInstanceEntity exerciseInstance;

    private Short reps;

    private Short weight;
}

