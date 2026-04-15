package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnersController {

    private final PartnerService partnerService;

    @GetMapping("/{partnerId}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long partnerId) {
        return ResponseEntity.ok(partnerService.findById(partnerId));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO saved = partnerService.create(request);
        return ResponseEntity.created(URI.create("/api/partners/" + saved.getId())).body(saved);
    }

    @PutMapping("/{partnerId}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long partnerId, @Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(partnerService.update(partnerId, request));
    }

    @DeleteMapping("/{partnerId}")
    public ResponseEntity<Void> delete(@PathVariable Long partnerId) {
        partnerService.delete(partnerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{partnerId}/deactivate")
    public ResponseEntity<UserResponseDTO> deactivate(@PathVariable Long partnerId) {
        return ResponseEntity.ok(partnerService.deactivate(partnerId));
    }

    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<UserResponseDTO>> listByAssociation(@PathVariable Long associationId) {
        return ResponseEntity.ok(partnerService.listByAssociation(associationId));
    }
}
