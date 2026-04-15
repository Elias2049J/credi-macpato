package com.runaitec.credimacpato.service;

import com.runaitec.credimacpato.dto.payment.PaymentRequestDTO;
import com.runaitec.credimacpato.dto.payment.PaymentResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentService {
    PaymentResponseDTO payVoucherItems(PaymentRequestDTO request);

    PaymentResponseDTO findPaymentById(Long paymentId);
    List<PaymentResponseDTO> listPaymentsByCustomer(Long customerId);
    List<PaymentResponseDTO> listPaymentsByVoucher(Integer voucherId);

    List<PaymentResponseDTO> listPaymentsByStandAndDateTimeBetween(Long standId, LocalDateTime from, LocalDateTime to);
}
