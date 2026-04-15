package com.runaitec.credimacpato.entity;

import com.runaitec.credimacpato.entity.user.Partner;
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
        @UniqueConstraint(name = "uk_puesto_socio_numero", columnNames = {"id_socio", "numero"})
    },
    indexes = {
        @Index(name = "idx_puesto_numero", columnList = "numero"),
        @Index(name = "idx_puesto_socio", columnList = "id_socio")
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_socio", nullable = false)
    private Partner partner;

    @OneToMany(mappedBy = "stand")
    private List<Voucher> vouchers = new ArrayList<>();
}
