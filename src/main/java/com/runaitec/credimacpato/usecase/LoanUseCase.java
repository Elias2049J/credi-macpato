package com.runaitec.credimacpato.usecase;

public interface LoanUseCase {
    void approveLoan(Long loanId);
    void denyLoan(Long loanId);
}

