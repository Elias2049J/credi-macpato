package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.BillingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Slf4j
public class BillingServiceImpl implements BillingService {
    @Override
    public VoucherResponseDTO issue(VoucherRequestDTO request) {
        return null;
    }

    @Override
    public VoucherResponseDTO findById(Long voucherId) {
        return null;
    }

    @Override
    public List<VoucherResponseDTO> listByStand(Long standId) {
        return List.of();
    }

    @Override
    public List<VoucherResponseDTO> listByCustomer(Long customerId) {
        return List.of();
    }

    @Override
    public List<VoucherResponseDTO> listPendingVouchersByCustomer(Long customerId) {
        return List.of();
    }

    @Override
    public List<VoucherResponseDTO> listPendingVouchersByPartner(Long partnerId) {
        return List.of();
    }

    @Override
    public List<VoucherResponseDTO> listVouchersByStandAndIssueDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to) {
        return List.of();
    }
}
