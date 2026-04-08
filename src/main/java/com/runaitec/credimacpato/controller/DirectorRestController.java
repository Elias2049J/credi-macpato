package com.runaitec.credimacpato.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.runaitec.credimacpato.dto.LoanDTO;
import com.runaitec.credimacpato.dto.LoanDisburseDTO;
import com.runaitec.credimacpato.entity.LoanRequestState;
import com.runaitec.credimacpato.facade.DirectorFacade;

@RestController
@RequestMapping("/api/directors")
public class DirectorRestController {

    private final DirectorFacade directorFacade;

    public DirectorRestController(DirectorFacade directorFacade) {
        this.directorFacade = directorFacade;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> directorHome() {
        return ResponseEntity.ok(Map.of("message", "Director API OK"));
    }

    @PostMapping("/requests/{solicitudId}/disburse")
    public ResponseEntity<LoanDisburseDTO> disburseMoney(@PathVariable Long solicitudId,
                                                         @RequestBody LoanDisburseDTO loanDisburseDTO) {
        LoanDisburseDTO result = directorFacade.disburseLoan(solicitudId, loanDisburseDTO);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/requests/{solicitudId}/evaluate")
    public ResponseEntity<Map<String, String>> evaluateLoanRequest(@PathVariable Long solicitudId) {
        // Obtenemos el estado real del facade
        LoanRequestState estadoFinal = directorFacade.evaluateLoanRequest(solicitudId);
        
        return ResponseEntity.ok(Map.of(
            "message", "Solicitud evaluada correctamente.",
            "estado", estadoFinal.name() // <-- Ahora el frontend sabrá si se aprobó o denegó
        ));
    }

    @PostMapping("/requests/{solicitudId}/approve")
    public ResponseEntity<LoanDTO> approveRequest(@PathVariable Long solicitudId) {
        LoanDTO result = directorFacade.approveRequest(solicitudId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/requests/{solicitudId}/deny")
    public ResponseEntity<Map<String, String>> denyRequest(@PathVariable Long solicitudId) {
        directorFacade.denyRequest(solicitudId);
        return ResponseEntity.ok(Map.of("message", "Solicitud denegada correctamente."));
    }

    @PostMapping("/requests/{solicitudId}/vote/{usuarioId}")
    public ResponseEntity<Map<String, String>> voteRequest(@PathVariable Long solicitudId,
                                                           @PathVariable Long usuarioId,
                                                           @RequestParam(name = "approved", defaultValue = "true") boolean approved) {
        directorFacade.voteRequest(solicitudId, usuarioId, approved);
        return ResponseEntity.ok(Map.of("message", "Voto registrado correctamente."));
    }
}