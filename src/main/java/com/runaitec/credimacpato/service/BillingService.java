package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BillingService {
    VoucherResponseDTO issueVoucher(VoucherRequestDTO request);

    VoucherResponseDTO findVoucherById(Integer voucherId);

    List<VoucherResponseDTO> listVouchersByStand(Long standId);
    List<VoucherResponseDTO> listVouchersByCustomer(Long customerId);

    List<VoucherResponseDTO> listPendingVouchersByCustomer(Long customerId);
    List<VoucherResponseDTO> listPendingVouchersByPartner(Long partnerId);

    List<VoucherResponseDTO> listVouchersByStandAndIssueDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to);
}
