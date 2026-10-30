package com.example.demo.controller;

import com.example.demo.dto.request.MensagemRequest;
import com.example.demo.dto.response.MensagemResponse;
import com.example.demo.service.MensagemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    private final MensagemService mensagemService;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    // Enviar mensagem
    @PostMapping
    public ResponseEntity<MensagemResponse> enviar(@Valid @RequestBody MensagemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagemService.enviarMensagem(request.conversaId(),
                request.remetenteId(),
                request.conteudoTexto()));
    }

    // Obter mensagens de uma conversa
    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<MensagemResponse>> listar(@PathVariable UUID conversaId) {
        return ResponseEntity.ok(mensagemService.listarPorConversa(conversaId));
    }

    // Marcar mensagens como lidas
    @PutMapping("/ler")
    public ResponseEntity<Void> marcarLidas(@RequestParam UUID conversaId,
                                            @RequestParam UUID usuarioId) {
        mensagemService.marcarComoLidas(conversaId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}