package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }
        return usuarioRepository.save(usuario);
    }
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }
    public Usuario bloquearUsuario(long id, String motivo) {
        Usuario usuario = buscarPorId(id);
        usuario.setMotivoBloqueio(motivo);
        return usuarioRepository.save(usuario);
    }
    public Usuario atualizarParcial(long id, Usuario dadosNovos) {
        Usuario usuarioExistente = buscarPorId(id);
        if (dadosNovos.getNome() != null) {
            usuarioExistente.setNome(dadosNovos.getNome());
        }
        if (dadosNovos.getEmail() != null) {
            if (usuarioRepository.existsByEmail(dadosNovos.getEmail())) {
                throw new RuntimeException("Este novo e-mail já está em uso!");
            }
            usuarioExistente.setEmail(dadosNovos.getEmail());
        }
        // Só atualiza a senha se tiver sido enviada
        if (dadosNovos.getSenhaHash() != null) {
            usuarioExistente.setSenhaHash(dadosNovos.getSenhaHash());
        }

        return usuarioRepository.save(usuarioExistente);
    }
}
