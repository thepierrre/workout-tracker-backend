package com.example.gymapp.domain.entities;

import com.example.gymapp.domain.dto.ExerciseDto;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "training_types")
public class TrainingTypeEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

//    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ExerciseEntity> exercises;


}
