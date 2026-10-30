package com.example.demo.controller;

import com.example.demo.dto.request.SuspensoesRequest;
import com.example.demo.dto.response.SuspensoesResponse;
import com.example.demo.service.SuspensoesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suspensoes")
public class SuspensoesController {

    private final SuspensoesService suspensoesService;

    public SuspensoesController(SuspensoesService suspensoesService) {
        this.suspensoesService = suspensoesService;
    }

    @PostMapping
    public ResponseEntity<SuspensoesResponse> solicitar(@Valid @RequestBody SuspensoesRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(suspensoesService.solicitarSuspensao(request));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<SuspensoesResponse>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(suspensoesService.listarPorUsuario(usuarioId));
    }

    @GetMapping
    public ResponseEntity<List<SuspensoesResponse>> listarTodas() {
        return ResponseEntity.ok(suspensoesService.listarTodas());
    }
}