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
            //UserEntity user
            ) {
        this.changeThreshold = changeThreshold;
        this.weightUnit = weightUnit;
        //this.user = user;
    }

    @Id
    @UuidGenerator
    private UUID id;

//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @JsonIgnoreProperties({"userSettings"})
//    private UserEntity user;

    private Double changeThreshold;

    private String weightUnit;
}
