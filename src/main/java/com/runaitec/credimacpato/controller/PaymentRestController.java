package com.runaitec.credimacpato.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runaitec.credimacpato.usecase.PaymentUseCase;

@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

    private final PaymentUseCase paymentUseCase;

    public PaymentRestController(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> paymentHome() {
        return ResponseEntity.ok(Map.of("message", "Payment API OK"));
    }

    @PostMapping("/{usuarioId}/make")
    public ResponseEntity<Map<String, String>> makePayment(@PathVariable Long usuarioId) {
        paymentUseCase.makePayment(usuarioId);
        return ResponseEntity.ok(Map.of("message", "Pago realizado correctamente."));
    }
}