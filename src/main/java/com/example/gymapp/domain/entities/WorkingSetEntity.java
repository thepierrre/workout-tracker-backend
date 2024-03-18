package com.example.gymapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sets")
public class WorkingSetEntity {

    @Id
    @GeneratedValue
    private Short id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_instance_id", referencedColumnName = "id")
    private ExerciseInstanceEntity exerciseInstance;

    private Short reps;

    private Short weight;
}

