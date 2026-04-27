package com.runaitec.credimacpato.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_comprobante")
public class VoucherItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cantidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private MeasureUnitType measureUnitType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_motivo_cobro", nullable = false)
    private Charge charge;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal unitValue = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal payableAmount = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private PaymentState state = PaymentState.PENDING;

    @ManyToOne
    @JoinColumn(name = "id_voucher", nullable = false)
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "id_pago")
    private Payment payment;

    public void calculateTotal() {
        payableAmount = quantity.multiply(unitValue).setScale(2, HALF_UP);
    }

    @PrePersist
    private void onCreate() {
        if (state==null){
            state = PaymentState.PENDING;
        }
    }

    public boolean isPaid() {
        return state == PaymentState.PAID;
    }
}
