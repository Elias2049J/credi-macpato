package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "asociacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Association extends User {
    @Column(name = "direccion")
    private String address;

    @Column(name = "razon_social")
    private String registrationName;

    @OneToOne
    private Account account;

    @OneToMany
    private List<Partner> partners;

    @Override
    public String getFullName() {
        return registrationName;
    }
}
