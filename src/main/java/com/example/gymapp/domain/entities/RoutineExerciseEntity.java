package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "routine_exercises")
public class RoutineExerciseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "routine_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"routineExercises", "user"})
    private RoutineEntity routine;

    @ManyToOne
    @JoinColumn(name = "exercise_type_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"user", "categories", "routineExercises"})
    private ExerciseTypeEntity exerciseType;

    @OneToMany(mappedBy = "routineExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("routineExercise")
    private List<BlueprintWorkingSetEntity> workingSets  = new ArrayList<>();

}
