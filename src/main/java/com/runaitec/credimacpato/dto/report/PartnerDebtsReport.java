package com.runaitec.credimacpato.dto.report;

import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PartnerDebtsReport extends Report {

    private Long customerId;

    private List<VoucherResponseDTO> vouchers;
}
