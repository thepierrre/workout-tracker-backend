package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public CategoryEntity(String name, MuscleGroup muscleGroup) {
        this.name = name;
        this.muscleGroup = muscleGroup;
    }

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private MuscleGroup muscleGroup;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<ExerciseTypeEntity> exerciseTypes;

    public enum MuscleGroup {
        OTHER("Other"),
        CORE("Core"),
        CHEST("Chest"),
        BACK("Back"),
        SHOULDERS("Shoulders"),
        ARMS("Arms"),
        LEGS("Legs");

        public final String name;
        private MuscleGroup(String name) {
            this.name = name;
        }
    }
}
