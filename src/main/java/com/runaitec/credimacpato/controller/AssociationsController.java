package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.service.AssociationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/associations")
@RequiredArgsConstructor
public class AssociationsController {

    private final AssociationService associationService;

    @GetMapping("/{associationId}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long associationId) {
        return ResponseEntity.ok(associationService.findById(associationId));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO saved = associationService.create(request);
        return ResponseEntity.created(URI.create("/api/associations/" + saved.getId())).body(saved);
    }

    @PutMapping("/{associationId}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long associationId, @Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(associationService.update(associationId, request));
    }

    @DeleteMapping("/{associationId}")
    public ResponseEntity<Void> delete(@PathVariable Long associationId) {
        associationService.delete(associationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{associationId}/deactivate")
    public ResponseEntity<UserResponseDTO> deactivate(@PathVariable Long associationId) {
        return ResponseEntity.ok(associationService.deactivate(associationId));
    }

    @GetMapping("/{associationId}/partners")
    public ResponseEntity<List<UserResponseDTO>> listPartners(@PathVariable Long associationId) {
        return ResponseEntity.ok(associationService.listPartners(associationId));
    }
}
