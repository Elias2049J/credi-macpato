package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.payment.PaymentRequestDTO;
import com.runaitec.credimacpato.dto.payment.PaymentResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentResponseDTO payVoucherItems(PaymentRequestDTO request);

    PaymentResponseDTO findById(Long paymentId);
    List<PaymentResponseDTO> listByCustomer(Long customerId);
    List<PaymentResponseDTO> listByVoucher(Integer voucherId);

    List<PaymentResponseDTO> listPaymentsByStandAndDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to);
}
