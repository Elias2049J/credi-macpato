package com.runaitec.credimacpato.usecase;

public interface DirectorsUseCase {
    void disburseMoney(Long solicitudId);
    void evaluateLoanRequest(Long solicitudId);
    void approveRequest(Long solicitudId);
    void denyRequest(Long solicitudId);
    void voteRequest(Long solicitudId, Long usuarioId);
}

