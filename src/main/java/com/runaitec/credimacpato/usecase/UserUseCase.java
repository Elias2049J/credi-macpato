package com.runaitec.credimacpato.usecase;

public interface UserUseCase {
    void leaveOrganization(Long usuarioId);
    void requestLoan(Long usuarioId);
    void getPaymentsCalendary(Long usuarioId);
    void getPaymentHistory(Long usuarioId);
}