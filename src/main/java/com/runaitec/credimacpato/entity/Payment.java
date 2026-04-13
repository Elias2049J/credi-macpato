package com.runaitec.credimacpato.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pago")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String code;

    @Column(name = "fecha_hora")
    private LocalDateTime dateTime;

    @Column(name = "monto")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "id_cuenta_destino")
    private Account destinationAccount;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "id_comprobante")
    private Voucher voucher;

    @ManyToMany
    @JoinTable(
            name = "pago_items_comprobante",
            joinColumns = @JoinColumn(name = "id_pago"),
            inverseJoinColumns = @JoinColumn(name = "id_item_comprobante")
    )
    private List<VoucherItem> paidItems;

    @PrePersist
    public void prePersist() {
        if (dateTime == null) {
            dateTime = LocalDateTime.now();
        }
        amount = Objects.requireNonNullElse(amount, BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }
}
