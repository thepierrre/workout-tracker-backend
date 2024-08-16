package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "exercise_instances")
public class ExerciseInstanceEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @OneToMany(mappedBy = "exerciseInstance", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("exerciseInstance")
    private List<InstanceWorkingSetEntity> workingSets = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "workout_id", referencedColumnName = "id")
    private WorkoutEntity workout;

    private String exerciseTypeName;

    private String notes;
}