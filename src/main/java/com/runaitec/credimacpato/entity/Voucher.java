package com.runaitec.credimacpato.entity;

import com.runaitec.credimacpato.entity.user.Customer;
import com.runaitec.credimacpato.entity.user.Partner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comprobante")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voucher", nullable = false)
    private Long id;

    @Column(name = "nro_serie")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private PaymentState state;

    @Column(name = "fecha_emision")
    private LocalDateTime issueDateTime;

    @Column(name = "porcentaje_igv", precision = 10, scale = 2)
    private BigDecimal igv = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Column(name = "valor_igv", precision = 10, scale = 2)
    private BigDecimal igv_amount = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Column(name = "total_valor_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal lineExtensionAmount = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Column(name = "monto_a_pagar", precision = 10, scale = 2)
    private BigDecimal payableAmount = BigDecimal.ZERO.setScale(2, HALF_UP);

    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoucherItem> voucherItems = new ArrayList<>();

    @OneToMany(mappedBy = "voucher")
    private List<Payment> payments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_emisor")
    private Partner issuer;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private Stand stand;

    @PrePersist
    private void onCreate() {
        if (issueDateTime == null)
            issueDateTime = LocalDateTime.now();
        if (state == null)
            state = PaymentState.PENDING;
    }

    public void calculateTotal() {
        voucherItems.forEach(VoucherItem::calculateTotal);
        lineExtensionAmount = voucherItems.stream()
                .map(VoucherItem::getPayableAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, HALF_UP);
        igv_amount = lineExtensionAmount.multiply(igv).setScale(2, HALF_UP);
        payableAmount = lineExtensionAmount.add(igv_amount).setScale(2, HALF_UP);
    }
}
