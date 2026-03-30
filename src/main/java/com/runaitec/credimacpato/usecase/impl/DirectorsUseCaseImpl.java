package com.runaitec.credimacpato.usecase.impl;

import com.runaitec.credimacpato.Exceptiones.ResourceNotFoundException;
import com.runaitec.credimacpato.dto.LoanDisburseDTO;
import com.runaitec.credimacpato.dto.LoanDTO;
import com.runaitec.credimacpato.dto.VoteDTO;
import com.runaitec.credimacpato.entity.Loan;
import com.runaitec.credimacpato.entity.LoanDisburse;
import com.runaitec.credimacpato.entity.LoanRequest;
import com.runaitec.credimacpato.entity.LoanRequestState;
import com.runaitec.credimacpato.entity.User;
import com.runaitec.credimacpato.entity.Vote;
import com.runaitec.credimacpato.mapper.LoanMapper;
import com.runaitec.credimacpato.mapper.TransactionMapper;
import com.runaitec.credimacpato.repository.LoanRepository;
import com.runaitec.credimacpato.repository.LoanRequestRepository;
import com.runaitec.credimacpato.repository.TransactionRepository;
import com.runaitec.credimacpato.repository.UserRepository;
import com.runaitec.credimacpato.repository.VoteRepository;
import com.runaitec.credimacpato.usecase.DirectorsUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Transactional
public class DirectorsUseCaseImpl implements DirectorsUseCase {

    private final LoanRequestRepository loanRequestRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final TransactionRepository transactionRepository;
    private final LoanMapper loanMapper;
    private final TransactionMapper transactionMapper;

    public DirectorsUseCaseImpl(LoanRequestRepository loanRequestRepository,
                                VoteRepository voteRepository,
                                UserRepository userRepository,
                                LoanRepository loanRepository,
                                TransactionRepository transactionRepository,
                                LoanMapper loanMapper,
                                TransactionMapper transactionMapper) {
        this.loanRequestRepository = loanRequestRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.transactionRepository = transactionRepository;
        this.loanMapper = loanMapper;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public LoanDisburseDTO disburseMoney(LoanDisburseDTO loanDisburseDTO) {
        LoanDisburse loanDisburse = (LoanDisburse) transactionMapper.toEntity(loanDisburseDTO);
        LoanDisburse saved = transactionRepository.save(loanDisburse);
        return (LoanDisburseDTO) transactionMapper.toDto(saved);
    }

    @Override
    public LoanRequestState evaluateLoanRequest(Long requestId) {
        LoanRequest request = loanRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("LoanRequest not found: " + requestId));

        if (!LoanRequestState.PENDING.equals(request.getStatus())) {
            return request.getStatus();
        }

        long yesVotes = request.getVotes() == null ? 0 : request.getVotes().stream().filter(Vote::getApproved).count();
        long noVotes = request.getVotes() == null ? 0 : request.getVotes().stream().filter(v -> !Boolean.TRUE.equals(v.getApproved())).count();

        LoanRequestState newState;
        if (yesVotes == 0 && noVotes == 0) {
            newState = LoanRequestState.PENDING;
        } else if (yesVotes > noVotes) {
            newState = LoanRequestState.APPROVED;
        } else {
            newState = LoanRequestState.DENIED;
        }

        if (newState != request.getStatus()) {
            request.setStatus(newState);
            loanRequestRepository.save(request);
        }

        return newState;
    }

    @Override
    public LoanDTO approveRequest(Long requestId) {
        LoanRequest request = loanRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("LoanRequest not found: " + requestId));

        request.setStatus(LoanRequestState.APPROVED);
        loanRequestRepository.save(request);

        Loan loan = new Loan();
        loan.setStartDate(LocalDateTime.now());
        loan.setDueDate(LocalDateTime.now().plusMonths(12));
        loan.setTermMonths(12);
        loan.setInterestRate(BigDecimal.ZERO);

        Loan persistedLoan = loanRepository.save(loan);
        return loanMapper.toDto(persistedLoan);
    }

    @Override
    public void denyRequest(Long requestId) {
        LoanRequest request = loanRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("LoanRequest not found: " + requestId));
        request.setStatus(LoanRequestState.DENIED);
        loanRequestRepository.save(request);
    }

    @Override
    public void voteRequest(VoteDTO voteDTO) {
        LoanRequest request = loanRequestRepository.findById(voteDTO.getLoanRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("LoanRequest not found: " + voteDTO.getLoanRequestId()));

        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + voteDTO.getUserId()));

        Vote vote = new Vote();
        vote.setLoanRequest(request);
        vote.setUser(user);
        vote.setApproved(voteDTO.getApproved());

        Vote saved = voteRepository.save(vote);

        if (request.getVotes() == null) {
            request.setVotes(new ArrayList<>());
        }
        request.getVotes().add(saved);
        loanRequestRepository.save(request);
    }
}