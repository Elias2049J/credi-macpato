package com.runaitec.credimacpato.dto.voucher;

import com.runaitec.credimacpato.entity.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoucherResponseDTO {
    private Long id;
    private String serialNumber;
    private PaymentState state;
    private LocalDate issueDate;
    private BigDecimal igv;
    private BigDecimal igvAmount;
    private BigDecimal lineExtensionAmount;
    private BigDecimal payableAmount;
    private List<VoucherItemResponseDTO> voucherItems;
    private Long issuerId;
    private String customerFullName;
    private Long customerId;
    private Long standId;
    private int standNumber;
    private BigDecimal paidAmount;
    private BigDecimal pendingAmount;
}
