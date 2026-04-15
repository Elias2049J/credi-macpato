package com.runaitec.credimacpato.dto.report;

import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
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
@ToString(callSuper = true)
public class CashClosureReportResponseDTO extends ReportResponseDTO {

    private Long standId;

    private LocalDate date;

    private BigDecimal totalSales;

    private Integer salesCount;

    private List<VoucherResponseDTO> vouchers;
}
