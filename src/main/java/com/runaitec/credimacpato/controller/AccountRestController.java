package com.runaitec.credimacpato.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.runaitec.credimacpato.dto.AccountDTO;
import com.runaitec.credimacpato.usecase.AccountUseCase;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    private final AccountUseCase accountUseCase;

    public AccountRestController(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> accountHome() {
        return ResponseEntity.ok(Map.of("message", "Account API OK"));
    }

    @PostMapping("/open")
    public ResponseEntity<Map<String, String>> openAccount(@RequestBody AccountDTO accountDTO) {
        accountUseCase.openAccount(accountDTO.getUsuarioId());
        return ResponseEntity.ok(Map.of("message", "Cuenta aperturada correctamente"));
    }

    @PostMapping("/{accountId}/close")
    public ResponseEntity<Map<String, String>> closeAccount(@PathVariable Long accountId) {
        accountUseCase.closeAccount(accountId);
        return ResponseEntity.ok(Map.of("message", "Cuenta cerrada correctamente"));
    }

    @PostMapping("/{accountId}/block")
    public ResponseEntity<Map<String, String>> blockAccount(@PathVariable Long accountId) {
        accountUseCase.blockAccount(accountId);
        return ResponseEntity.ok(Map.of("message", "Cuenta bloqueada correctamente"));
    }
}