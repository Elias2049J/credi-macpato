package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.service.AssociationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/associations")
@RequiredArgsConstructor
public class AssociationController {

    private final AssociationService associationService;

    @GetMapping("/{associationId}/vendors")
    public ResponseEntity<List<UserResponseDTO>> listVendors(@PathVariable Long associationId) {
        return ResponseEntity.ok(associationService.listVendors(associationId));
    }

    @GetMapping("/{associationId}/customers")
    public ResponseEntity<List<UserResponseDTO>> listCustomers(@PathVariable Long associationId) {
        return ResponseEntity.ok(associationService.listCustomers(associationId));
    }

    @GetMapping("/{associationId}/members")
    public ResponseEntity<List<UserResponseDTO>> listMembers(@PathVariable Long associationId) {
        return ResponseEntity.ok(associationService.listMembers(associationId));
    }
}
