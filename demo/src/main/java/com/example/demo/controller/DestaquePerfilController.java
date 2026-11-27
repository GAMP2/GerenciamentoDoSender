package com.example.demo.controller;

import com.example.demo.entity.DestaquePerfil;
import com.example.demo.service.DestaquePerfilService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios/{usuarioId}/destaques")
public class DestaquePerfilController {

    private final DestaquePerfilService service;

    public DestaquePerfilController(DestaquePerfilService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DestaquePerfil> criar(@PathVariable UUID usuarioId, @RequestBody DestaquePerfil destaque) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(usuarioId, destaque));
    }

    @GetMapping
    public ResponseEntity<List<DestaquePerfil>> listar(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(service.listar(usuarioId));
    }

    @PatchMapping("/{destaqueId}")
    public ResponseEntity<DestaquePerfil> atualizar(@PathVariable UUID destaqueId, @RequestBody DestaquePerfil dadosNovos) {
        return ResponseEntity.ok(service.atualizar(destaqueId, dadosNovos));
    }

    @DeleteMapping("/{destaqueId}")
    public ResponseEntity<Void> deletar(@PathVariable UUID destaqueId) {
        service.deletar(destaqueId);
        return ResponseEntity.noContent().build();
    }
}