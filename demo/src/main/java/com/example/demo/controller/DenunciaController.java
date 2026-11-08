package com.example.demo.controller;

import com.example.demo.dto.request.DenunciaRequest;
import com.example.demo.dto.response.DenunciaResponse;
import com.example.demo.service.DenunciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {
    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @PostMapping
    public ResponseEntity<DenunciaResponse> registrar(@RequestBody @Valid DenunciaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(denunciaService.registrar(request));
    }

    @GetMapping
    public ResponseEntity<List<DenunciaResponse>> listar(@RequestParam(required = false) String status) {
        if (status != null) {
            return ResponseEntity.ok(denunciaService.listarPorStatus(status));
        }
        return ResponseEntity.ok(denunciaService.listarTodas());
    }

    @GetMapping("/usuario/{idDenunciado}")
    public ResponseEntity<List<DenunciaResponse>> obterHistorico(@PathVariable UUID idDenunciado) {
        return ResponseEntity.ok(denunciaService.historicoDoDenunciado(idDenunciado));
    }
}
