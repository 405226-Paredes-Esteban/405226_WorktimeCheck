package com.scaffold.template.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_state")
    private Long userState;

    @Column(name = "user_audUser")
    private Long userAud;

    @Column(name = "user_role")
    private String userRole;
    /*@ManyToMany
    @JoinTable(
            name = "Users_Roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles; // Relación con los roles

    // Métod para devolver los roles como una cadena separada por comas
    public String getRolesAsString() {
        return roles.stream()
                .map(RoleEntity::getDescription)
                .collect(Collectors.joining(","));
    }*/
}