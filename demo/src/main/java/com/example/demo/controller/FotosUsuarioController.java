package com.example.demo.controller;

import com.example.demo.dto.response.FotosUsuarioResponse;
import com.example.demo.service.FotosUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/fotos")
public class FotosUsuarioController {

    private final FotosUsuarioService fotosUsuarioService;

    public FotosUsuarioController(FotosUsuarioService fotosUsuarioService) {
        this.fotosUsuarioService = fotosUsuarioService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FotosUsuarioResponse>> listarPorUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(fotosUsuarioService.listarPorUsuario(usuarioId));
    }

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<FotosUsuarioResponse> adicionarFoto(@PathVariable UUID usuarioId, @RequestParam String fotoUrl) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fotosUsuarioService.adicionarFoto(usuarioId, fotoUrl));
    }

    @DeleteMapping("/{fotoId}")
    public ResponseEntity<Void> deletarFoto(@PathVariable UUID fotoId) {
        fotosUsuarioService.deletarFoto(fotoId);
        return ResponseEntity.noContent().build();
    }
}