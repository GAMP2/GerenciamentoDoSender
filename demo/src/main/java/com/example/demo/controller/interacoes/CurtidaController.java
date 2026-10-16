package com.example.demo.controller.interacoes;

import com.example.demo.model.interacoes.Curtidas;
import com.example.demo.service.interacoes.CurtidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curtidas")
public class CurtidaController {
    @Autowired
    private CurtidaService curtidaService;

    //Rota para curtir e descurtir
    @PostMapping("/alternar")
    public ResponseEntity<String> alternarCurtida(@RequestParam long idOrigem, @RequestParam long idDestino) {
        String mensagem = curtidaService.alternarCurtida(idOrigem, idDestino);
        return ResponseEntity.ok(mensagem);
    }

     //Rota para contar quantas curtidas um usuário recebeu
    @GetMapping("/count/{idDestino}")
    public ResponseEntity<Long> contarCurtidas(@PathVariable long idDestino) {
        long total = curtidaService.contarCurtidasRecebidas(idDestino);
        return ResponseEntity.ok(total);
    }
    //Rota que lista quem curtiu quem
    @GetMapping("/usuario/{idDestino}")
    public ResponseEntity<List<Curtidas>> listarCurtidasRecebidas(@PathVariable long idDestino) {
        List<Curtidas> lista = curtidaService.listarCurtidasRecebidas(idDestino);
        return ResponseEntity.ok(lista);
    }
}
