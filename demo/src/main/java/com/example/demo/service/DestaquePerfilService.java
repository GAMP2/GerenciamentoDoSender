package com.example.demo.service;

import com.example.demo.entity.DestaquePerfil;
import com.example.demo.repository.DestaquePerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DestaquePerfilService {

    private final DestaquePerfilRepository repository;
    private final UsuarioService usuarioService;

    public DestaquePerfilService(DestaquePerfilRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    public DestaquePerfil criar(UUID usuarioId, DestaquePerfil destaque) {
        destaque.setUsuario(usuarioService.buscarEntidade(usuarioId));
        return repository.save(destaque);
    }

    public List<DestaquePerfil> listar(UUID usuarioId) {
        return repository.findByUsuarioIdOrderByCriadoEmAsc(usuarioId);
    }

    public DestaquePerfil atualizar(UUID destaqueId, DestaquePerfil dadosNovos) {
        DestaquePerfil atual = repository.findById(destaqueId)
                .orElseThrow(() -> new RuntimeException("Destaque não encontrado!"));

        if (dadosNovos.getTitulo() != null) atual.setTitulo(dadosNovos.getTitulo());
        if (dadosNovos.getCapaUrl() != null) atual.setCapaUrl(dadosNovos.getCapaUrl());
        if (dadosNovos.getMidiaUrl() != null) atual.setMidiaUrl(dadosNovos.getMidiaUrl());

        return repository.save(atual);
    }

    public void deletar(UUID destaqueId) {
        repository.deleteById(destaqueId);
    }
}