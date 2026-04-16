package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.debt.BulkDebtUploadRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/debts")
@RequiredArgsConstructor
public class DebtController {

    private final BillingService billingService;

    @PostMapping
    public ResponseEntity<VoucherResponseDTO> createDebt(@Valid @RequestBody VoucherRequestDTO request) {
        VoucherResponseDTO saved = billingService.issue(request);
        return ResponseEntity.created(URI.create("/api/vouchers/" + saved.getId())).body(saved);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<VoucherResponseDTO>> bulkUpload(@Valid @RequestBody BulkDebtUploadRequestDTO request) {
        return ResponseEntity.status(501).build();
    }
}
