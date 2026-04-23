package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.FileType;
import com.runaitec.credimacpato.dto.debt.BulkDebtUploadResponseDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import org.springframework.web.multipart.MultipartFile;

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

    BulkDebtUploadResponseDTO importDebtsBulk(MultipartFile file, FileType fileType);
}
