package com.runaitec.credimacpato.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "vendedor_empresa",
        indexes = {
                @Index(name = "idx_vendedor_empresa_razon_social", columnList = "razon_social")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessVendor extends Vendor {
    @Column(name = "direccion")
    private String address;

    @Column(name = "razon_social")
    private String registrationName;
}
