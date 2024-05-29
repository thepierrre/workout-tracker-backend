package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    private List<ExerciseTypeEntity> exerciseTypes;

    public void addExerciseType(ExerciseTypeEntity exerciseType) {
        this.exerciseTypes.add(exerciseType);
        exerciseType.getCategories().add(this);
    }

    public void removeExerciseType(ExerciseTypeEntity exerciseType) {
        this.exerciseTypes.remove(exerciseType);
        exerciseType.getCategories().remove(this);
    }

}
