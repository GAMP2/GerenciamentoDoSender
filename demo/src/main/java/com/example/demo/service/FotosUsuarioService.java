package com.example.demo.service;

import com.example.demo.dto.response.FotosUsuarioResponse;
import com.example.demo.entity.FotosUsuario;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.FotosUsuarioRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FotosUsuarioService {

    private final FotosUsuarioRepository fotosUsuarioRepository;
    private final UsuarioRepository usuarioRepository;

    public FotosUsuarioService(FotosUsuarioRepository fotosUsuarioRepository,
                               UsuarioRepository usuarioRepository) {
        this.fotosUsuarioRepository = fotosUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<FotosUsuarioResponse> listarPorUsuario(UUID usuarioId) {
        return fotosUsuarioRepository.findByUsuarioId(usuarioId).stream()
                .map(foto -> new FotosUsuarioResponse(
                        foto.getId(),
                        foto.getUsuario().getId(),
                        foto.getFotoUrl()
                ))
                .toList();
    }

    @Transactional
    public FotosUsuarioResponse adicionarFoto(UUID usuarioId, String fotoUrl) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        FotosUsuario foto = new FotosUsuario(usuario, fotoUrl);
        FotosUsuario fotoSalva = fotosUsuarioRepository.save(foto);

        return new FotosUsuarioResponse(
                fotoSalva.getId(),
                usuario.getId(),
                fotoSalva.getFotoUrl()
        );
    }

    @Transactional
    public void deletarFoto(UUID fotoId) {
        if (!fotosUsuarioRepository.existsById(fotoId)) {
            throw new IllegalArgumentException("Foto não encontrada");
        }
        fotosUsuarioRepository.deleteById(fotoId);
    }
}