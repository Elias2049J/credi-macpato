package com.runaitec.credimacpato.dto.voucher;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VoucherRequestDTO {

    @NotBlank
    private String serialNumber;

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 2, fraction = 2)
    private BigDecimal igv;

    @NotEmpty
    @Valid
    private List<VoucherItemRequestDTO> voucherItems;

    @NotNull
    private Long issuerId;

    @NotNull
    private Long customerId;

    @NotNull
    private Long standId;
}
