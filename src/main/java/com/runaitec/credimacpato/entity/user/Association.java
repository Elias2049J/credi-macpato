package com.runaitec.credimacpato.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
    name = "asociacion",
    indexes = {
        @Index(name = "idx_asociacion_razon_social", columnList = "razon_social")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Association extends BusinessPartner {
    @OneToMany
    private List<Partner> partners;
}
