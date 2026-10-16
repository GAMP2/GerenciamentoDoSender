package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable long id) {
        usuarioService.bloquearUsuario(id, "Desativado a pedido do utilizador");
        return ResponseEntity.ok("Usuário desativado com sucesso!");
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> atualizarParcial(@PathVariable long id, @RequestBody Usuario dadosNovos) {
        Usuario usuarioAtualizado = usuarioService.atualizarParcial(id, dadosNovos);
        return ResponseEntity.ok(usuarioAtualizado);
    }}