package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_settings")
public class UserSettingsEntity {

    public UserSettingsEntity (
            Double changeThreshold,
            String weightUnit
            ) {
        this.changeThreshold = changeThreshold;
        this.weightUnit = weightUnit;
    }

    @Id
    @UuidGenerator
    private UUID id;

    private Double changeThreshold;

    private String weightUnit;
}
