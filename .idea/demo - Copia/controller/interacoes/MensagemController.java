package com.example.demo.controller.interacoes;

import com.example.demo.dto.request.MensagemRequest;
import com.example.demo.dto.response.MensagemResponse;
import com.example.demo.service.interacoes.MensagemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagemService.enviarMensagem(request));
    }

    // Obter mensagens de uma conversa
    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<MensagemResponse>> listar(@PathVariable Long conversaId) {
        return ResponseEntity.ok(mensagemService.listarMensagensDaConversa(conversaId));
    }

    // Marcar mensagens como lidas
    @PutMapping("/ler")
    public ResponseEntity<Void> marcarLidas(@RequestParam Long conversaId,
                                            @RequestParam Long usuarioId) {
        mensagemService.marcarComoLidas(conversaId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}