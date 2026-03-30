package com.runaitec.credimacpato.facade;

import org.springframework.stereotype.Component;

import com.runaitec.credimacpato.dto.LoanDisburseDTO;
import com.runaitec.credimacpato.dto.LoanDTO;
import com.runaitec.credimacpato.dto.VoteDTO;
import com.runaitec.credimacpato.entity.LoanRequestState;
import com.runaitec.credimacpato.usecase.DirectorsUseCase;

@Component
public class DirectorFacade {

    private final DirectorsUseCase directorsUseCase;

    public DirectorFacade(DirectorsUseCase directorsUseCase) {
        this.directorsUseCase = directorsUseCase;
    }

    public LoanDisburseDTO disburseLoan(Long requestId, LoanDisburseDTO loanDisburseDTO) {
        LoanRequestState status = directorsUseCase.evaluateLoanRequest(requestId);
        if (!LoanRequestState.APPROVED.equals(status)) {
            throw new IllegalStateException("No es posible desembolsar porque la solicitud está en estado: " + status);
        }
        return directorsUseCase.disburseMoney(loanDisburseDTO);
    }

    public LoanRequestState evaluateLoanRequest(Long requestId) {
        return directorsUseCase.evaluateLoanRequest(requestId);
    }

    public LoanDTO approveRequest(Long requestId) {
        return directorsUseCase.approveRequest(requestId);
    }

    public void denyRequest(Long requestId) {
        directorsUseCase.denyRequest(requestId);
    }

    public void voteRequest(Long requestId, Long userId, boolean approved) {
        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setLoanRequestId(requestId);
        voteDTO.setUserId(userId);
        voteDTO.setApproved(approved);
        directorsUseCase.voteRequest(voteDTO);
    }

    public LoanDisburseDTO evaluateApproveAndDisburse(Long requestId, LoanDisburseDTO disburseDTO) {
        LoanRequestState state = evaluateLoanRequest(requestId);
        
        if (LoanRequestState.APPROVED.equals(state)) {
            approveRequest(requestId);
            return disburseLoan(requestId, disburseDTO);
        }
        
        throw new IllegalStateException("No se puede desembolsar: la evaluación resultó en estado " + state);
    }
}