package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Data
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "routines_exercise_types",
            joinColumns = @JoinColumn(name = "routine_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_type_id")
    )
    @JsonIgnoreProperties("exerciseInstances")
    @OrderColumn(name = "exercise_order")
    private List<ExerciseTypeEntity> exerciseTypes;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"routines", "password", "email", "workouts"})
    private UserEntity user;

}
