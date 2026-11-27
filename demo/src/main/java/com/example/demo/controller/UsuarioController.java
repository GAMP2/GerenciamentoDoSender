package com.example.demo.controller;

import com.example.demo.dto.request.UsuarioRequest;
import com.example.demo.dto.response.UsuarioResponse;
import com.example.demo.entity.Usuario;
import com.example.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /usuarios -> cadastrar
    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse novoUsuario = usuarioService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // GET /usuarios -> listar
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET /usuarios/{id} -> buscar um
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // DELETE /usuarios/{id} -> desativar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /usuarios/{id} -> atualizar só alguns campos
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarParcial(@PathVariable UUID id,
                                                            @RequestBody Usuario dadosNovos) {
        return ResponseEntity.ok(usuarioService.atualizarParcial(id, dadosNovos));
    }
}