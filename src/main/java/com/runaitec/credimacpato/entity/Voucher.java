package com.runaitec.credimacpato.entity;

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
    private Integer id;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_voucher")
    private List<VoucherItem> voucherItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_emisor")
    private User issuer;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "id_puesto")
    private Stand stand;

    @OneToMany
    private List<Debt> debt;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();


    @PrePersist
    private void onCreate() {
        if (this.issueDateTime == null)
            issueDateTime = LocalDateTime.now();
    }

    public void calculateTotal() {
        this.voucherItems.forEach(VoucherItem::calculateTotal);
        this.lineExtensionAmount = voucherItems.stream()
                .map(VoucherItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, HALF_UP);
        this.igv_amount = lineExtensionAmount.multiply(igv);
        this.payableAmount = lineExtensionAmount.subtract(igv_amount);
    }
}
