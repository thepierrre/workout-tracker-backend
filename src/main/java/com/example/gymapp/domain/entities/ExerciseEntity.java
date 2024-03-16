package com.example.gymapp.domain.entities;

import com.example.gymapp.domain.dto.TrainingTypeDto;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "exercise_types")
public class ExerciseEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "training_type_id", referencedColumnName = "id")
    @JsonIgnore
    private TrainingTypeEntity trainingType;
}
