package com.runaitec.credimacpato.entity.user;

import com.runaitec.credimacpato.entity.Stand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "owner")
    private List<Stand> stands = new ArrayList<>();

    @OneToMany(mappedBy = "association")
    private List<Vendor> vendors = new ArrayList<>();

    @OneToMany(mappedBy = "association")
    private List<Customer> customers = new ArrayList<>();
}
