package com.example.gymapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "workouts")
public class WorkoutEntity {

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
