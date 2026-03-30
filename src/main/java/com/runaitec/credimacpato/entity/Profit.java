package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "utilidad")
@Getter
@Setter
public class Profit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "monto")
    private BigDecimal amount;
    @Column(name = "fecha_hora")
    private LocalDateTime localDateTime;
    @Column(name = "aporte_total")
    private BigDecimal totalContribution;
    @Column(name = "proporcion")
    private BigDecimal proportion;

    @ManyToOne
    private User user;

    @PrePersist
    public void prePersist() {
        amount = Objects.requireNonNullElse(amount, BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }
}
