package com.example.demo.controller.interacoes;

import com.example.demo.dto.request.ConversaRequest;
import com.example.demo.dto.response.ConversaResponse;
import com.example.demo.service.interacoes.ConversaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversas")
public class ConversaController {

    private final ConversaService conversaService;

    public ConversaController(ConversaService conversaService) {
        this.conversaService = conversaService;
    }

    @PostMapping
    public ResponseEntity<ConversaResponse> iniciar(@Valid @RequestBody ConversaRequest request) {
        return ResponseEntity.ok(conversaService.iniciarConversa(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(conversaService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ConversaResponse>> listarDoUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(conversaService.listarConversasDoUsuario(usuarioId));
    }
}