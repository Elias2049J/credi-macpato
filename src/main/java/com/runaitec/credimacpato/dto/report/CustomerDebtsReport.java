package com.runaitec.credimacpato.dto.report;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.dto.user.customer.CustomerResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
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
@ToString(callSuper = true)
public class CustomerDebtsReport extends Report {
    private long totalVouchersCount;
    private BigDecimal totalDebt;
    private UserResponseDTO customer;
    private List<VoucherResponseDTO> vouchers;
}
