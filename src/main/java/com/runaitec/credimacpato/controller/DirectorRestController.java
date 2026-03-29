package com.runaitec.credimacpato.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runaitec.credimacpato.usecase.DirectorsUseCase;

@RestController
@RequestMapping("/api/directors")
public class DirectorRestController {

    private final DirectorsUseCase directorsUseCase;

    public DirectorRestController(DirectorsUseCase directorsUseCase) {
        this.directorsUseCase = directorsUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> directorHome() {
        return ResponseEntity.ok(Map.of("message", "Director API OK"));
    }

    @PostMapping("/requests/{solicitudId}/disburse")
    public ResponseEntity<Map<String, String>> disburseMoney(@PathVariable Long solicitudId) {
        directorsUseCase.disburseMoney(solicitudId);
        return ResponseEntity.ok(Map.of("message", "Desembolso realizado correctamente."));
    }

    @PostMapping("/requests/{solicitudId}/evaluate")
    public ResponseEntity<Map<String, String>> evaluateLoanRequest(@PathVariable Long solicitudId) {
        directorsUseCase.evaluateLoanRequest(solicitudId);
        return ResponseEntity.ok(Map.of("message", "Solicitud evaluada correctamente."));
    }

    @PostMapping("/requests/{solicitudId}/approve")
    public ResponseEntity<Map<String, String>> approveRequest(@PathVariable Long solicitudId) {
        directorsUseCase.approveRequest(solicitudId);
        return ResponseEntity.ok(Map.of("message", "Solicitud aprobada correctamente."));
    }

    @PostMapping("/requests/{solicitudId}/deny")
    public ResponseEntity<Map<String, String>> denyRequest(@PathVariable Long solicitudId) {
        directorsUseCase.denyRequest(solicitudId);
        return ResponseEntity.ok(Map.of("message", "Solicitud denegada correctamente."));
    }

    @PostMapping("/requests/{solicitudId}/vote/{usuarioId}")
    public ResponseEntity<Map<String, String>> voteRequest(@PathVariable Long solicitudId,
                                                           @PathVariable Long usuarioId) {
        directorsUseCase.voteRequest(solicitudId, usuarioId);
        return ResponseEntity.ok(Map.of("message", "Voto registrado correctamente."));
    }
}