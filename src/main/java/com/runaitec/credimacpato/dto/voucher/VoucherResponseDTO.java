package com.runaitec.credimacpato.dto.voucher;

import com.runaitec.credimacpato.entity.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoucherResponseDTO {
    private Integer id;

    private String serialNumber;

    private PaymentState state;

    private LocalDateTime issueDateTime;

    private BigDecimal igv;

    private BigDecimal igvAmount;

    private BigDecimal lineExtensionAmount;

    private BigDecimal payableAmount;

    private List<VoucherItemResponseDTO> voucherItems;

    private Long issuerId;

    private Long customerId;

    private Long standId;

    private List<Long> paymentIds;
}
