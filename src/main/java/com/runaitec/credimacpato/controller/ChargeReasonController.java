package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.chargeReason.ChargeRequestDTO;
import com.runaitec.credimacpato.dto.chargeReason.ChargeResponseDTO;
import com.runaitec.credimacpato.service.ChargeService;
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

    private final ChargeService chargeService;

    @GetMapping
    public ResponseEntity<List<ChargeResponseDTO>> findAll() {
        return ResponseEntity.ok(chargeService.findAll());
    }

    @GetMapping("/stand/{standId}")
    public ResponseEntity<List<ChargeResponseDTO>> findAllByStand(@PathVariable Long standId) {
        return ResponseEntity.ok(chargeService.findAllByStand(standId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(chargeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ChargeResponseDTO> create(@Valid @RequestBody ChargeRequestDTO request) {
        ChargeResponseDTO saved = chargeService.create(request);
        return ResponseEntity.created(URI.create("/api/charge-reasons/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargeResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ChargeRequestDTO request) {
        return ResponseEntity.ok(chargeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chargeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
