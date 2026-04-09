package com.runaitec.credimacpato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nro_cuenta")
    private Long accountNumber;

    @Column(name = "monto")
    private BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "id_socio")
    private Partner partner;

    @OneToOne
    @JoinColumn(name = "id_asociacion")
    private Association association;

    @OneToMany
    private List<Payment> payments;
}
