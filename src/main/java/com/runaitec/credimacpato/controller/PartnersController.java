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

    @GetMapping("/association/{associationId}")
    public ResponseEntity<List<UserResponseDTO>> listByAssociation(@PathVariable Long associationId) {
        return ResponseEntity.ok(partnerService.listByAssociation(associationId));
    }
}
