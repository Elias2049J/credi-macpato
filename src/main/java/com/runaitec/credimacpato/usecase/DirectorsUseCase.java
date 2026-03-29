package com.runaitec.credimacpato.usecase;

import com.runaitec.credimacpato.dto.LoanDTO;
import com.runaitec.credimacpato.dto.LoanDisburseDTO;
import com.runaitec.credimacpato.dto.VoteDTO;
import com.runaitec.credimacpato.entity.LoanRequestState;

public interface DirectorsUseCase {
    LoanDisburseDTO disburseMoney(LoanDisburseDTO loanDisburseDTO);
    LoanRequestState evaluateLoanRequest(Long requestId);
    LoanDTO approveRequest(Long requestId);
    void denyRequest(Long requestId);
    void voteRequest(VoteDTO voteDTO);
}

