package com.example.demo.controller.interacoes;

import com.example.demo.dto.request.BloqueioRequest;
import com.example.demo.dto.response.BloqueioResponse;
import com.example.demo.service.interacoes.BloqueioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bloqueios")
public class BloqueioController {

    private final BloqueioService bloqueioService;

    public BloqueioController(BloqueioService bloqueioService) {
        this.bloqueioService = bloqueioService;
    }

    @PostMapping
    public ResponseEntity<BloqueioResponse> bloquear(@Valid @RequestBody BloqueioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bloqueioService.bloquearUsuario(request));
    }

    @DeleteMapping
    public ResponseEntity<Void> desbloquear(@RequestParam Long idBloqueador,
                                            @RequestParam Long idBloqueado) {
        bloqueioService.desbloquearUsuario(idBloqueador, idBloqueado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idBloqueador}")
    public ResponseEntity<List<BloqueioResponse>> listarBloqueados(@PathVariable Long idBloqueador) {
        return ResponseEntity.ok(bloqueioService.listarBloqueados(idBloqueador));
    }
}