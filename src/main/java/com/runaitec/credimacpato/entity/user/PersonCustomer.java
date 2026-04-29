package com.runaitec.credimacpato.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente_persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonCustomer extends Customer {
    @Column(name = "nombres")
    private String name;

    @Column(name = "apellidos")
    private String lastname;

    @Override
    public String getFullName() {
        return name + " " + lastname;
    }
}
