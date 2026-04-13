package com.runaitec.credimacpato.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuario_persona")
public abstract class PersonUser extends User {
    @Column(name = "nombres")
    private String name;

    @Column(name = "apellidos")
    private String lastname;

    @Override
    public String getFullName() {
        return name + " " + lastname;
    }
}
