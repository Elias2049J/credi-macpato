package com.runaitec.credimacpato.dto.debt;

import com.runaitec.credimacpato.dto.voucher.VoucherItemRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class BulkDebtRowRequestDTO {

    @NotNull
    private Long standId;

    @NotNull
    private Long issuerId;

    @NotNull
    private Long customerId;

    private LocalDate issueDate;

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 2, fraction = 2)
    private BigDecimal igv;

    @NotEmpty
    @Valid
    private List<VoucherItemRequestDTO> voucherItems;
}

