package com.runaitec.credimacpato.dto.report;

import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VouchersByStandDTO {
    private Long totalIssuedVouchersCount;
    private BigDecimal totalSales;
    private BigDecimal totalDebt;
    private StandResponseDTO stand;
    private List<VoucherResponseDTO> vouchers;
}
