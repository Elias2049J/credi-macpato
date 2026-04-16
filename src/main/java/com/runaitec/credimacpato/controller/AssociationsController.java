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
public class AssociationsController {

    private final AssociationService associationService;

    @GetMapping("/{associationId}/partners")
    public ResponseEntity<List<UserResponseDTO>> listPartners(@PathVariable Long associationId) {
        return ResponseEntity.ok(associationService.listPartners(associationId));
    }
}
