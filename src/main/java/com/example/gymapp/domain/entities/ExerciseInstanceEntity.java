package com.example.gymapp.domain.entities;

import com.example.gymapp.domain.dto.ExerciseTypeDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "exercise_instances")
public class ExerciseInstanceEntity {

    @Id
    @UuidGenerator
    UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_type_id", referencedColumnName = "id")
    ExerciseTypeEntity exerciseType;

//    SetDto set;
}
