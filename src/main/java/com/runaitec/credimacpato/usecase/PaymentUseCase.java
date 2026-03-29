package com.runaitec.credimacpato.usecase;

import com.runaitec.credimacpato.dto.PaymentDTO;

import java.util.List;

public interface PaymentUseCase{
    PaymentDTO makePayment(PaymentDTO paymentDTO);
    List<PaymentDTO> getPaymentsCalendary(Long userId);
    List<PaymentDTO> getPaymentHistory(Long userId);
}