package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.entity.MeasureUnitType;
import com.runaitec.credimacpato.entity.Voucher;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface BillingService {
    VoucherResponseDTO issue(VoucherRequestDTO request);

    VoucherResponseDTO findById(Long voucherId);

    List<VoucherResponseDTO> listByStand(Long standId);
    List<VoucherResponseDTO> listByCustomer(Long customerId);

    List<VoucherResponseDTO> listPendingVouchersByCustomer(Long customerId);
    List<VoucherResponseDTO> listPendingVouchersByIssuer(Long issuerId);

    List<VoucherResponseDTO> listVouchersByStandAndIssueDateBetween(Long standId, LocalDate from, LocalDate to);

    String generateVoucherSerial(Voucher voucher);

    MeasureUnitType[] listUnits();

    List<VoucherResponseDTO> listByIssuer(Long issuerId);
}
