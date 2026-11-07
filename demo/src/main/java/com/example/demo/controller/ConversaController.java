package com.example.demo.controller;

import com.example.demo.dto.request.ConversaRequest;
import com.example.demo.dto.response.ConversaResponse;
import com.example.demo.service.ConversaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/conversas")
public class ConversaController {

    private final ConversaService conversaService;

    public ConversaController(ConversaService conversaService) {
        this.conversaService = conversaService;
    }

    @PostMapping
    public ResponseEntity<ConversaResponse> iniciarConversa(@RequestBody @Valid ConversaRequest request) {
        ConversaResponse response = conversaService.iniciarConversa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversaResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(conversaService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ConversaResponse>> listarConversasDoUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(conversaService.listarConversasDoUsuario(usuarioId));
    }
}