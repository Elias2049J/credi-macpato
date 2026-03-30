package com.runaitec.credimacpato.usecase;

public interface LoanUseCase {
    void getPaymentsCalendary(Long userId);
    void getPaymentHistory(Long userId);
    void requestLoan(Long userId);
}

