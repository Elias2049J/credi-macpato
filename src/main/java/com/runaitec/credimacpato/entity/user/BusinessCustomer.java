package com.runaitec.credimacpato.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cliente_empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessCustomer extends Customer {
    @Column(name = "direccion")
    private String address;

    @Column(name = "razon_social")
    private String registrationName;

    @Override
    public String getFullName() {
        return registrationName;
    }
}
