package com.runaitec.credimacpato.controller;

import com.runaitec.credimacpato.dto.user.UserRequestDTO;
import com.runaitec.credimacpato.dto.user.UserResponseDTO;
import com.runaitec.credimacpato.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO saved = userService.register(request);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<UserResponseDTO> disable(@PathVariable Long id) {
        return ResponseEntity.ok(userService.disable(id));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<UserResponseDTO> enable(@PathVariable Long id) {
        return ResponseEntity.ok(userService.enable(id));
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<UserResponseDTO> block(@PathVariable Long id) {
        return ResponseEntity.ok(userService.block(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchByName(
            @RequestParam("string") String q) {
        return ResponseEntity.ok(userService.searchByName(q));
    }
}
