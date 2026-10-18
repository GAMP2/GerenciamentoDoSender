package com.example.demo.service;

import com.example.demo.dto.request.UsuarioRequest;
import com.example.demo.dto.response.UsuarioResponse;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioMapper usuarioMapper,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Cadastrar um usuário novo
    public UsuarioResponse salvar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username já está em uso!");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setSenhaHash(passwordEncoder.encode(request.senha())); // hash da senha

        Usuario salvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(salvo);
    }

    // Listar só os usuários que não foram desativados
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findByDeletadoEmIsNull().stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(UUID id) {
        return usuarioMapper.toResponse(buscarEntidade(id));
    }

    // Uso interno: devolve a entidade (para outros services). Usuário desativado conta como "não encontrado".
    public Usuario buscarEntidade(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        if (usuario.getDeletadoEm() != null) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        return usuario;
    }

    // "Exclusão lógica": o usuário continua no banco, mas marcado como apagado
    public void desativar(UUID id) {
        Usuario usuario = buscarEntidade(id);
        usuario.setDeletadoEm(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    public UsuarioResponse atualizarParcial(UUID id, Usuario dadosNovos) {
        Usuario usuarioExistente = buscarEntidade(id);

        if (dadosNovos.getNome() != null) {
            usuarioExistente.setNome(dadosNovos.getNome());
        }
        // só confere e-mail repetido se o e-mail realmente mudou
        if (dadosNovos.getEmail() != null
                && !dadosNovos.getEmail().equals(usuarioExistente.getEmail())) {
            if (usuarioRepository.existsByEmail(dadosNovos.getEmail())) {
                throw new RuntimeException("Este novo e-mail já está em uso!");
            }
            usuarioExistente.setEmail(dadosNovos.getEmail());
        }

        return usuarioMapper.toResponse(usuarioRepository.save(usuarioExistente));
    }
}