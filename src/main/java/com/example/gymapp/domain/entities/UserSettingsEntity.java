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

    public UserSettingsEntity (
            Double changeThreshold,
            String weightUnit,
            UserEntity user
            ) {
        this.changeThreshold = changeThreshold;
        this.weightUnit = weightUnit;
        this.user = user;
    }

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
