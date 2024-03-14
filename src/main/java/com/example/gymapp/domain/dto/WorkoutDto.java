package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.TrainingTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDto {

    @Id
    @UuidGenerator
    UUID id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    UserEntity user_id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    TrainingTypeEntity training_type_id;

    Date date;
}
