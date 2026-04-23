package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final BillingService billingService;

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.findById(id));
    }

    @GetMapping("/stand/{standId}")
    public ResponseEntity<List<VoucherResponseDTO>> listByStand(@PathVariable Long standId) {
        return ResponseEntity.ok(billingService.listByStand(standId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<VoucherResponseDTO>> listByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(billingService.listByCustomer(customerId));
    }

    @PostMapping
    public ResponseEntity<VoucherResponseDTO> issue(@Valid @RequestBody VoucherRequestDTO request) {
        VoucherResponseDTO saved = billingService.issue(request);
        return ResponseEntity.created(URI.create("/api/vouchers/" + saved.getId())).body(saved);
    }

    @GetMapping("/customer/{customerId}/pending")
    public ResponseEntity<List<VoucherResponseDTO>> getPendingVouchersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(billingService.listPendingVouchersByCustomer(customerId));
    }

    @GetMapping("/issuer/{issuerId}/pending")
    public ResponseEntity<List<VoucherResponseDTO>> getPendingVouchersByIssuer(@PathVariable Long issuerId) {
        return ResponseEntity.ok(billingService.listPendingVouchersByIssuer(issuerId));
    }

    @GetMapping("/stand/{standId}")
    public ResponseEntity<List<VoucherResponseDTO>> getVouchersByStandDateBetween(
            @PathVariable Long standId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return ResponseEntity.ok(billingService.listVouchersByStandAndIssueDateBetween(standId, from, to));
    }
}
