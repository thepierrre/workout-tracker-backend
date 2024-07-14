package com.example.gymapp.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_settings")
public class UserSettingsEntity {

    @Id
    @UuidGenerator
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    private UserEntity user;

    private Double changeThreshold;

    private String weightUnit;
}
