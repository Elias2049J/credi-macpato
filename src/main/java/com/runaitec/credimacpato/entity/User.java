package com.runaitec.credimacpato.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario")
    private String username;

    @Column(name = "nombres")
    private String name;

    @Column(name = "apellidos")
    private String lastname;

    @Column(name = "clave")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "puesto")
    private Role role;

    @Column(name = "activo")
    private Boolean active;

    @Column(name = "fecha_creacion")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans;


    public String getFullName() {
        return name + " " + lastname;
    }

    public void deactivate() {
        this.active = false;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}
