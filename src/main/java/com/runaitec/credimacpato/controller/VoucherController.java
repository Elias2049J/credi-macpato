package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.voucher.VoucherRequestDTO;
import com.runaitec.credimacpato.dto.voucher.VoucherResponseDTO;
import com.runaitec.credimacpato.entity.MeasureUnitType;
import com.runaitec.credimacpato.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/issuer/{issuerId}")
    public ResponseEntity<List<VoucherResponseDTO>> listByIssuer(@PathVariable Long issuerId) {
        return ResponseEntity.ok(billingService.listByIssuer(issuerId));
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

    @GetMapping(value = "/stand/{standId}", params = {"from", "to"})
    public ResponseEntity<List<VoucherResponseDTO>> getVouchersByStandDateBetween(
            @PathVariable Long standId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(billingService.listVouchersByStandAndIssueDateBetween(standId, from, to));
    }

    @GetMapping("/unit-types")
    public ResponseEntity<MeasureUnitType[]> getUnitTypes() {
        return ResponseEntity.ok(billingService.listUnits());
    }
}
