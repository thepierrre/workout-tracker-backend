package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "routines")
public class RoutineEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"routine"})
    @OrderColumn(name = "exercise_order")
    private List<RoutineExerciseEntity> routineExercises  = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private UserEntity user;

    public void removeRoutineExercise(RoutineExerciseEntity routineExercise) {
        routineExercises.remove(routineExercise);
        routineExercise.setRoutine(null);
    }

}
