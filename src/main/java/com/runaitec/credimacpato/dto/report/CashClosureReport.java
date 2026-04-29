package com.runaitec.credimacpato.dto.report;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.dto.user.vendor.VendorResponseDTO;
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
public class CashClosureReport extends Report {
    private UserResponseDTO owner;
    private BigDecimal totalSalesToday;
    private BigDecimal totalDebtToday;

    private Long totalVouchersCountToday;

    private List<VouchersByStandDTO> vouchersByStand;
}
