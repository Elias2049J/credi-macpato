package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prestamo")
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha_limite")
    private LocalDateTime dueDate;

    @Column(name = "fecha_inicio")
    private LocalDateTime startDate;

    @Column(name = "plazo_meses")
    private Integer termMonths;

    @Column(name = "tasa_interes")
    private BigDecimal interestRate;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @PrePersist
    public void prePersist() {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        interestRate = Objects.requireNonNullElse(interestRate, BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }
}
