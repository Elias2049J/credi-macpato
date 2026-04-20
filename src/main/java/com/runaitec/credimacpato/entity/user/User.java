package com.runaitec.credimacpato.entity.user;

import com.runaitec.credimacpato.entity.Role;
import com.runaitec.credimacpato.entity.UserState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(
    name = "usuario",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_nombre_usuario", columnNames = {"nombre_usuario"})
    },
    indexes = {
        @Index(name = "idx_usuario_nombre_usuario", columnList = "nombre_usuario")
    }
)
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

    @Column(name = "nombre_usuario")
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

    public void deactivate() {
        this.state = UserState.DISABLED;
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
            state = UserState.ENABLED;
    }

    @Override
    public boolean isEnabled() {
        return state.isActive();
    }
}
