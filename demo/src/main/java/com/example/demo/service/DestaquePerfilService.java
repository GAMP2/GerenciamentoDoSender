package com.example.demo.service;

import com.example.demo.dto.request.DestaqueRequest;
import com.example.demo.dto.response.DestaqueResponse;
import com.example.demo.entity.DestaquePerfil;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.DestaquePerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DestaquePerfilService {

    private final DestaquePerfilRepository destaqueRepository;
    private final UsuarioService usuarioService;

    public DestaquePerfilService(DestaquePerfilRepository destaqueRepository, UsuarioService usuarioService) {
        this.destaqueRepository = destaqueRepository;
        this.usuarioService = usuarioService;
    }

    public DestaqueResponse criar(UUID usuarioId, DestaqueRequest request) {
        Usuario usuario = usuarioService.buscarEntidade(usuarioId);

        DestaquePerfil destaque = new DestaquePerfil();
        destaque.setUsuario(usuario);
        destaque.setTitulo(request.titulo());
        destaque.setCapaUrl(request.capaUrl());
        destaque.setMidiaUrl(request.midiaUrl());

        return toResponse(destaqueRepository.save(destaque));
    }

    public List<DestaqueResponse> listarPorUsuario(UUID usuarioId) {
        usuarioService.buscarEntidade(usuarioId);
        return destaqueRepository.findByUsuarioIdOrderByCriadoEmAsc(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    public void deletar(UUID destaqueId) {
        DestaquePerfil destaque = destaqueRepository.findById(destaqueId)
                .orElseThrow(() -> new RuntimeException("Destaque não encontrado!"));
        destaqueRepository.delete(destaque);
    }

    private DestaqueResponse toResponse(DestaquePerfil destaque) {
        return new DestaqueResponse(
                destaque.getId(),
                destaque.getTitulo(),
                destaque.getCapaUrl(),
                destaque.getMidiaUrl(),
                destaque.getCriadoEm()
        );
    }
}