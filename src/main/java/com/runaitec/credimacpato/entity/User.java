package com.runaitec.credimacpato.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario")
    private String username;

    @Column(name = "clave")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private UserState state;

    @Column(name = "fecha_creacion")
    private LocalDateTime createdAt;

    public abstract String getFullName();

    public void deactivate() {
        this.state = UserState.UNACTIVE;
    }

    public void block() {
        this.state = UserState.BLOCKED;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null)
            createdAt = LocalDateTime.now();
        if (state == null)
            state = UserState.ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return state.isActive();
    }
}
