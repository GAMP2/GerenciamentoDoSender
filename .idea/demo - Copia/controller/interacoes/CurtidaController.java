package com.example.demo.controller.interacoes;

import com.example.demo.dto.request.CurtidaRequest;
import com.example.demo.dto.response.CurtidaResponse;
import com.example.demo.dto.response.CurtidaStatusResponse;
import com.example.demo.model.interacoes.Curtidas;
import com.example.demo.service.interacoes.CurtidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curtidas")
public class CurtidaController {

    private final CurtidaService curtidaService;

    public CurtidaController(CurtidaService curtidaService) {
        this.curtidaService = curtidaService;
    }

    @PostMapping
    public ResponseEntity<CurtidaStatusResponse> alternar(@Valid @RequestBody CurtidaRequest request) {
        return ResponseEntity.ok(curtidaService.alternarCurtida(request));
    }

    @GetMapping("/{idDestino}/total")
    public ResponseEntity<Long> contar(@PathVariable Long idDestino) {
        return ResponseEntity.ok(curtidaService.contarCurtidasRecebidas(idDestino));
    }

    @GetMapping("/{idDestino}")
    public ResponseEntity<List<CurtidaResponse>> listar(@PathVariable Long idDestino) {
        return ResponseEntity.ok(curtidaService.listarCurtidasRecebidas(idDestino));
    }
}