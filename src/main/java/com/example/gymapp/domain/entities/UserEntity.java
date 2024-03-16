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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TrainingTypeEntity> trainingTypes;
}
