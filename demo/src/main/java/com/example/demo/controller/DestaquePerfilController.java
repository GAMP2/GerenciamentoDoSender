package com.example.demo.controller;

import com.example.demo.dto.request.DestaqueRequest;
import com.example.demo.dto.response.DestaqueResponse;
import com.example.demo.service.DestaquePerfilService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios/{usuarioId}/destaques")
public class DestaquePerfilController {

    private final DestaquePerfilService destaqueService;

    public DestaquePerfilController(DestaquePerfilService destaqueService) {
        this.destaqueService = destaqueService;
    }

    @PostMapping
    public ResponseEntity<DestaqueResponse> criar(@PathVariable UUID usuarioId,
                                                  @Valid @RequestBody DestaqueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(destaqueService.criar(usuarioId, request));
    }

    @GetMapping
    public ResponseEntity<List<DestaqueResponse>> listar(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(destaqueService.listarPorUsuario(usuarioId));
    }

    @DeleteMapping("/{destaqueId}")
    public ResponseEntity<Void> deletar(@PathVariable UUID usuarioId,
                                        @PathVariable UUID destaqueId) {
        destaqueService.deletar(destaqueId);
        return ResponseEntity.noContent().build();
    }
}