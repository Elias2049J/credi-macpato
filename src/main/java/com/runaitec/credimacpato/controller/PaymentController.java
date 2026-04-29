package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.payment.PaymentRequestDTO;
import com.runaitec.credimacpato.dto.payment.PaymentResponseDTO;
import com.runaitec.credimacpato.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentResponseDTO>> listByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(paymentService.listByCustomer(customerId));
    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<List<PaymentResponseDTO>> listByVoucher(@PathVariable Long voucherId) {
        return ResponseEntity.ok(paymentService.listByVoucher(voucherId));
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> payVoucherItems(@Valid @RequestBody PaymentRequestDTO request) {
        PaymentResponseDTO paid = paymentService.payVoucherItems(request);
        return ResponseEntity.created(URI.create("/api/payments/" + paid.getId())).body(paid);
    }

    @GetMapping("/stand/{standId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByStandAndDateTimeBetween(
            @PathVariable Long standId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(paymentService.listPaymentsByStandAndDateTimeBetween(standId, from, to));
    }
}
