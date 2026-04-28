package com.runaitec.credimacpato.entity;

import com.runaitec.credimacpato.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "puesto",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_puesto_propietario_numero", columnNames = {"id_propietario", "numero"})
    },
    indexes = {
        @Index(name = "idx_puesto_numero", columnList = "numero"),
        @Index(name = "idx_puesto_propietario", columnList = "id_propietario")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero", nullable = false)
    private Integer number;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_propietario", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "stand")
    private List<Voucher> vouchers = new ArrayList<>();

    @OneToMany(mappedBy = "stand")
    private List<Charge> chargeReasons = new ArrayList<>();
}
