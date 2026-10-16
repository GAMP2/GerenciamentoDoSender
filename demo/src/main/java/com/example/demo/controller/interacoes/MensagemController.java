package com.example.demo.controller.interacoes;

import com.example.demo.model.interacoes.Mensagem;
import com.example.demo.service.interacoes.MensagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    // Enviar mensagem
    @PostMapping
    public ResponseEntity<Mensagem> enviar(@RequestParam long conversaId,
                                           @RequestParam long remetenteId,
                                           @RequestParam long destinatarioId,
                                           @RequestParam(required = false) String conteudoTexto,
                                           @RequestParam(required = false) String urlImagem) {
        Mensagem mensagem = mensagemService.enviarMensagem(conversaId, remetenteId, destinatarioId, conteudoTexto, urlImagem);
        return ResponseEntity.ok(mensagem);
    }

    // Obter mensagens de uma conversa
    @GetMapping("/conversa/{conversaId}")
    public ResponseEntity<List<Mensagem>> listar(@PathVariable long conversaId) {
        List<Mensagem> lista = mensagemService.listarMensagensDaConversa(conversaId);
        return ResponseEntity.ok(lista);
    }

    // Marcar mensagens como lidas
    @PutMapping("/ler")
    public ResponseEntity<String> marcarLidas(@RequestParam long conversaId, @RequestParam long usuarioId) {
        mensagemService.marcarComoLidas(conversaId, usuarioId);
        return ResponseEntity.ok("Mensagens marcadas como lidas.");
    }
}