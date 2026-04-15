package com.runaitec.credimacpato.controller;

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
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final BillingService billingService;

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(billingService.findVoucherById(id));
    }

    @GetMapping("/stand/{standId}")
    public ResponseEntity<List<VoucherResponseDTO>> listByStand(@PathVariable Long standId) {
        return ResponseEntity.ok(billingService.listVouchersByStand(standId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<VoucherResponseDTO>> listByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(billingService.listVouchersByCustomer(customerId));
    }

    @PostMapping
    public ResponseEntity<VoucherResponseDTO> issue(@Valid @RequestBody VoucherRequestDTO request) {
        VoucherResponseDTO saved = billingService.issueVoucher(request);
        return ResponseEntity.created(URI.create("/api/vouchers/" + saved.getId())).body(saved);
    }
}
