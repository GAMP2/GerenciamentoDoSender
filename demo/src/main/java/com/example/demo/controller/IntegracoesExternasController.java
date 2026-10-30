package com.example.demo.controller;

import com.example.demo.dto.request.IntegracoesExternasRequest;
import com.example.demo.dto.response.IntegracoesExternasResponse;
import com.example.demo.service.IntegracoesExternasService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/integracoes-externas")
public class IntegracoesExternasController {

    private final IntegracoesExternasService integracoesService;

    public IntegracoesExternasController(IntegracoesExternasService integracoesService) {
        this.integracoesService = integracoesService;
    }

    @PostMapping
    public ResponseEntity<IntegracoesExternasResponse> salvar(@Valid @RequestBody IntegracoesExternasRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(integracoesService.salvar(request));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<IntegracoesExternasResponse>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(integracoesService.listarPorUsuario(usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        integracoesService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}