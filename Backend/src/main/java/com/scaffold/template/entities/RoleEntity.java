package com.scaffold.template.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_description")
    private String description;

    @Column(name = "role_state")
    private int state;

    @Column(name = "role_auduser")
    private Long audUser;

/*    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users; // Relación con los usuarios*/
}
