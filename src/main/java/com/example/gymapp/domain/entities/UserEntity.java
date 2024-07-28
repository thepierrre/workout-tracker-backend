package com.example.gymapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    public UserEntity(
            String username,
            String email,
            String password,
            List<Role> roles
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Id
    @UuidGenerator
    private UUID id;

    private String username;

    private String password;

    @JsonIgnore
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"exercises", "user"})
    private List<RoutineEntity> routines;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkoutEntity> workouts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ExerciseTypeEntity> exerciseTypes;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private UserSettingsEntity userSettings;

}
