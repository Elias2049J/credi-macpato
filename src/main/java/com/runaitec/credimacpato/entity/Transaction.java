package com.runaitec.credimacpato.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime date;

    @Column(name = "monto")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "cuenta_origen_id")
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "cuenta_destino_id")
    private Account destinationAccount;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDateTime.now();
        }
        amount = Objects.requireNonNullElse(amount, BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }
}
