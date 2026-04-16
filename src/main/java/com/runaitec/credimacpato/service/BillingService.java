package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BillingService {
    VoucherResponseDTO issue(VoucherRequestDTO request);

    VoucherResponseDTO findById(Long voucherId);

    List<VoucherResponseDTO> listByStand(Long standId);
    List<VoucherResponseDTO> listByCustomer(Long customerId);

    List<VoucherResponseDTO> listPendingVouchersByCustomer(Long customerId);
    List<VoucherResponseDTO> listPendingVouchersByPartner(Long partnerId);

    List<VoucherResponseDTO> listVouchersByStandAndIssueDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to);
}
