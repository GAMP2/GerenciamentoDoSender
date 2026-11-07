package com.example.demo.controller;

import com.example.demo.dto.request.BloqueioRequest;
import com.example.demo.dto.response.BloqueioResponse;
import com.example.demo.service.BloqueioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bloqueios")
public class BloqueioController {

    private final BloqueioService bloqueioService;

    public BloqueioController(BloqueioService bloqueioService) {
        this.bloqueioService = bloqueioService;
    }

    @PostMapping
    public ResponseEntity<BloqueioResponse> bloquearUsuario(@RequestBody @Valid BloqueioRequest request) {
        BloqueioResponse response = bloqueioService.bloquearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> desbloquearUsuario(@RequestParam UUID idBloqueador,
                                                   @RequestParam UUID idBloqueado) {
        bloqueioService.desbloquearUsuario(idBloqueador, idBloqueado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{idBloqueador}")
    public ResponseEntity<List<BloqueioResponse>> listarBloqueados(@PathVariable UUID idBloqueador) {
        return ResponseEntity.ok(bloqueioService.listarBloqueados(idBloqueador));
    }
}