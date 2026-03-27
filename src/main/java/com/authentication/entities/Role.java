package com.authentication.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "user_roles")
public class Role {

    @Id
    private UUID roleId =  UUID.randomUUID();

    @Column(unique = true   ,nullable = false)
    private String roleName;
}
