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
    private Integer id;

    @Column(name = "numero_orden")
    private Integer orderNumber;

    @Column(name = "cantidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private MeasureUnitType measureUnitType;

    @Column(name = "cantidad_base_unidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseQuantity = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Column(name = "descripcion")
    private String description;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal unitValue = BigDecimal.ZERO.setScale(2, HALF_UP);

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO.setScale(2, HALF_UP);

    @OneToOne
    private Debt debt;

    public void calculateTotal() {
        total = quantity.multiply(unitValue);
    }
}
