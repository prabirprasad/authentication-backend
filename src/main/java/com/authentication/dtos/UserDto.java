package com.authentication.dtos;

import com.authentication.entities.ProviderType;
import com.authentication.entities.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {

    private UUID userId;

    private String email;

    private String name;

    private String password;

    private boolean enabled;
    private String image;

    private Instant createdAt =  Instant.now();
    private Instant updatedAt = Instant.now();

    private String gender;

    private ProviderType providerType;
    private Set<Role> roles =  new HashSet<>();

}
