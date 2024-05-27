package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "training_types")
public class RoutineEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("exerciseInstances")
    private List<ExerciseTypeEntity> exerciseTypes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"trainingRoutines", "password", "email", "workouts"})
    private UserEntity user;

//    @OneToMany(mappedBy = "trainingRoutine", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private List<WorkoutEntity> workouts;

}
