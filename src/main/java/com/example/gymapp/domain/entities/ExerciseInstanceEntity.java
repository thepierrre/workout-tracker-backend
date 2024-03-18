package com.example.gymapp.domain.entities;

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
@Table(name = "exercise_instances")
public class ExerciseInstanceEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_type_id", referencedColumnName = "id")
    private ExerciseTypeEntity exerciseType;

    @OneToMany(mappedBy = "exerciseInstance", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SetEntity> sets;
}
