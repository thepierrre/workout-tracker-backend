package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "exercise_types")
public class ExerciseTypeEntity {

    public ExerciseTypeEntity(
            String name,
            List<CategoryEntity> categories,
            Equipment equipment,
            Boolean isDefault
    ) {
        this.name = name;
        this.categories = categories;
        this.equipment = equipment;
        this.isDefault = isDefault;

    }

    @Id
    @UuidGenerator
    private UUID id;

    private Boolean isDefault;

    private String name;

    @Enumerated(EnumType.STRING)
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"routines", "password", "email", "workouts", "roles", "exerciseTypes"})
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "exercise_type_categories",
            joinColumns = @JoinColumn(name = "exercise_type_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("exerciseTypes")
    private List<CategoryEntity> categories = new ArrayList<>();

    @OneToMany(mappedBy = "exerciseType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RoutineExerciseEntity> routineExercises = new ArrayList<>();

    public enum Equipment {
        DUMBBELLS("dumbbells"),
        BARBELL("barbell"),
        WEIGHT_PLATES("weight plates"),
        KETTLEBELLS("kettlebells"),
        MACHINE("machine"),
        BODYWEIGHT("bodyweight"),
        BAR("bar");

        public final String name;

        private Equipment(String name) { this.name = name; }
    }


}
