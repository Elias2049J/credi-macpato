package com.runaitec.credimacpato.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "socio_empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessPartner extends Partner {
    @Column(name = "direccion")
    private String address;

    @Column(name = "razon_social")
    private String registrationName;
}
