package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "deuda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private DebtState state;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_socio")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private Stand stand;

    @OneToOne
    @JoinColumn(name = "id_comprobante")
    private Voucher voucher;

    @PrePersist
    public void prePersist() {
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
    }
}
