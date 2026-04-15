package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.chargeReason.ChargeReasonRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeReasonResponseDTO;
import com.runaitec.credimacpato.service.ChargeReasonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/charge-reasons")
@RequiredArgsConstructor
public class ChargeReasonController {

    private final ChargeReasonService chargeReasonService;

    @GetMapping
    public ResponseEntity<List<ChargeReasonResponseDTO>> findAll() {
        return ResponseEntity.ok(chargeReasonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargeReasonResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(chargeReasonService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ChargeReasonResponseDTO> create(@Valid @RequestBody ChargeReasonRequestDTO request) {
        ChargeReasonResponseDTO saved = chargeReasonService.create(request);
        return ResponseEntity.created(URI.create("/api/charge-reasons/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargeReasonResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ChargeReasonRequestDTO request) {
        return ResponseEntity.ok(chargeReasonService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargeReasonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
