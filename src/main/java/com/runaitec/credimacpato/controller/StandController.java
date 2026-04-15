package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.stand.StandRequestDTO;
import com.runaitec.credimacpato.dto.stand.StandResponseDTO;
import com.runaitec.credimacpato.service.StandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/stands")
@RequiredArgsConstructor
public class StandController {

    private final StandService standService;

    @GetMapping
    public ResponseEntity<List<StandResponseDTO>> findAll() {
        return ResponseEntity.ok(standService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(standService.findById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<StandResponseDTO>> listByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(standService.listStandsByOwner(ownerId));
    }

    @PostMapping
    public ResponseEntity<StandResponseDTO> create(@Valid @RequestBody StandRequestDTO request) {
        StandResponseDTO saved = standService.create(request);
        return ResponseEntity.created(URI.create("/api/stands/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandResponseDTO> update(@PathVariable Long id, @Valid @RequestBody StandRequestDTO request) {
        return ResponseEntity.ok(standService.update(id, request));
    }

    @PostMapping("/{id}/change-owner/{newOwnerId}")
    public ResponseEntity<StandResponseDTO> changeOwner(@PathVariable Long id, @PathVariable Long newOwnerId) {
        return ResponseEntity.ok(standService.changeOwner(id, newOwnerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        standService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
