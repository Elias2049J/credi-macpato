package com.runaitec.credimacpato.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuenta")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nro_cuenta")
    private Long accountNumber;

    @Column(name = "fecha_apertura")
    private LocalDateTime openingDate;

    @Column(name = "monto")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cuenta")
    private AccountState accountState;

    @PrePersist
    public void prePersist() {
        if (openingDate == null) {
            openingDate = LocalDateTime.now();
        }
    }
}
