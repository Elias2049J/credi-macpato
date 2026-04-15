package com.runaitec.credimacpato.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "socio_persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonPartner extends Partner {
    @Column(name = "nombres")
    private String name;

    @Column(name = "apellidos")
    private String lastname;

    public String getFullName() {
        return name + " " + lastname;
    }

    @ManyToOne
    private Association association;
}
