package com.runaitec.credimacpato.service.impl;

import com.runaitec.credimacpato.dto.payment.PaymentRequestDTO;
import com.runaitec.credimacpato.dto.payment.PaymentResponseDTO;
import com.runaitec.credimacpato.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PaymentSeviceImpl implements PaymentService {
    @Override
    public PaymentResponseDTO payVoucherItems(PaymentRequestDTO request) {
        return null;
    }

    @Override
    public PaymentResponseDTO findById(Long paymentId) {
        return null;
    }

    @Override
    public List<PaymentResponseDTO> listByCustomer(Long customerId) {
        return List.of();
    }

    @Override
    public List<PaymentResponseDTO> listByVoucher(Integer voucherId) {
        return List.of();
    }

    @Override
    public List<PaymentResponseDTO> listPaymentsByStandAndDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to) {
        return List.of();
    }
}
