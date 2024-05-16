package com.example.gymapp.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "roles")
public class Role {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
}
