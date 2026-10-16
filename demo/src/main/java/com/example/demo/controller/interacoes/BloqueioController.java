package com.example.demo.controller.interacoes;

import com.example.demo.model.interacoes.Bloqueios;
import com.example.demo.service.interacoes.BloqueiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bloqueios")
public class BloqueioController {

    @Autowired
    private BloqueiosService bloqueioService;

    @PostMapping
    public ResponseEntity<String> bloquear(@RequestParam long idBloqueador,
                                           @RequestParam long idBloqueado,
                                           @RequestParam(required = false) String motivo) {
        String resposta = bloqueioService.bloquearUsuario(idBloqueador, idBloqueado, motivo);
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping
    public ResponseEntity<String> desbloquear(@RequestParam long idBloqueador,
                                              @RequestParam long idBloqueado) {
        String resposta = bloqueioService.desbloquearUsuario(idBloqueador, idBloqueado);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{idBloqueador}")
    public ResponseEntity<List<Bloqueios>> listarBloqueados(@PathVariable long idBloqueador) {
        List<Bloqueios> lista = bloqueioService.listarBloqueados(idBloqueador);
        return ResponseEntity.ok(lista);
    }
}