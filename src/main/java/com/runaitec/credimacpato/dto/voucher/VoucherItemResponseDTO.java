package com.runaitec.credimacpato.dto.voucher;

import com.runaitec.credimacpato.dto.chargeReason.ChargeResponseDTO;
import com.runaitec.credimacpato.entity.MeasureUnitType;
import com.runaitec.credimacpato.entity.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoucherItemResponseDTO {
    private Long id;

    private BigDecimal quantity;

    private MeasureUnitType measureUnitType;

    private ChargeResponseDTO charge;

    private BigDecimal unitValue;

    private BigDecimal payableAmount;

    private PaymentState state;

    private Long voucherId;

    private Long paymentId;
}
