package com.runaitec.credimacpato.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runaitec.credimacpato.usecase.UserUseCase;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserUseCase userUseCase;

    public UserRestController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> userHome() {
        return ResponseEntity.ok(Map.of("message", "User API OK"));
    }

    @PostMapping("/{usuarioId}/leave")
    public ResponseEntity<Map<String, String>> leaveOrganization(@PathVariable Long usuarioId) {
        userUseCase.leaveOrganization(usuarioId);
        return ResponseEntity.ok(Map.of("message", "El usuario salió de la organización correctamente."));
    }

    @PostMapping("/{usuarioId}/request-loan")
    public ResponseEntity<Map<String, String>> requestLoan(@PathVariable Long usuarioId) {
        userUseCase.requestLoan(usuarioId);
        return ResponseEntity.ok(Map.of("message", "Solicitud de préstamo registrada correctamente."));
    }

    @GetMapping("/{usuarioId}/payments-calendar")
    public ResponseEntity<Map<String, String>> getPaymentsCalendary(@PathVariable Long usuarioId) {
        userUseCase.getPaymentsCalendary(usuarioId);
        return ResponseEntity.ok(Map.of("message", "Consulta de calendario de pagos ejecutada correctamente."));
    }

    @GetMapping("/{usuarioId}/payment-history")
    public ResponseEntity<Map<String, String>> getPaymentHistory(@PathVariable Long usuarioId) {
        userUseCase.getPaymentHistory(usuarioId);
        return ResponseEntity.ok(Map.of("message", "Consulta de historial de pagos ejecutada correctamente."));
    }
}