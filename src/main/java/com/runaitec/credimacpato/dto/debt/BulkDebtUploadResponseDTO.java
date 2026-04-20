package com.runaitec.credimacpato.dto.debt;

import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BulkDebtUploadResponseDTO {

    private Integer requestedCount;

    private Integer createdCount;

    private List<VoucherResponseDTO> createdVouchers = new ArrayList<>();

    private List<BulkDebtUploadErrorDTO> errors = new ArrayList<>();
}
