package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.*;
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
@Table(name = "exercise_types")
public class ExerciseTypeEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "exerciseType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ExerciseInstanceEntity> exerciseInstances;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"routines", "password", "email", "workouts", "roles", "exerciseTypes"})
    private UserEntity user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "exercise_type_category",
            joinColumns = @JoinColumn(name = "exercise_type_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("exerciseTypes")
    private List<CategoryEntity> categories;

    @ManyToMany(mappedBy = "exerciseTypes")
    @JsonIgnore
    private List<RoutineEntity> routines;

}
