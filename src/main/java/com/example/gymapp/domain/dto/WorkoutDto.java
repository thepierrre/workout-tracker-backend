package com.example.gymapp.domain.dto;

import com.example.gymapp.domain.entities.TrainingTypeEntity;
import com.example.gymapp.domain.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDto {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private UserEntity user_id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private TrainingTypeEntity training_type_id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
}
