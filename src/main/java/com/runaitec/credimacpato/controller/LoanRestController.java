package com.runaitec.credimacpato.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runaitec.credimacpato.usecase.LoanUseCase;

@RestController
@RequestMapping("/api/loans")
public class LoanRestController {

    private final LoanUseCase loanUseCase;

    public LoanRestController(LoanUseCase loanUseCase) {
        this.loanUseCase = loanUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> loanHome() {
        return ResponseEntity.ok(Map.of("message", "Loan API OK"));
    }

    @PostMapping("/{loanId}/approve")
    public ResponseEntity<Map<String, String>> approveLoan(@PathVariable Long loanId) {
        loanUseCase.approveLoan(loanId);
        return ResponseEntity.ok(Map.of("message", "Préstamo aprobado correctamente."));
    }

    @PostMapping("/{loanId}/deny")
    public ResponseEntity<Map<String, String>> denyLoan(@PathVariable Long loanId) {
        loanUseCase.denyLoan(loanId);
        return ResponseEntity.ok(Map.of("message", "Préstamo denegado correctamente."));
    }
}