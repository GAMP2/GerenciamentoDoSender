package com.example.demo.service;

import com.example.demo.dto.request.UsuarioRequest;
import com.example.demo.dto.response.UsuarioResponse;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

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

    public UsuarioResponse salvar(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        usuario.setDataCriacao(LocalDate.now());
        usuario.setIdade(Period.between(request.dtNasc(), LocalDate.now()).getYears());

        Usuario salvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(salvo);
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(long id) {
        return usuarioMapper.toResponse(buscarEntidade(id));
    }

    public Usuario bloquearUsuario(long id, String motivo) {
        Usuario usuario = buscarEntidade(id);
        usuario.setMotivoBloqueio(motivo);
        return usuarioRepository.save(usuario);
    }

    public UsuarioResponse atualizarParcial(long id, Usuario dadosNovos) {
        Usuario usuarioExistente = buscarEntidade(id);
        if (dadosNovos.getNome() != null) {
            usuarioExistente.setNome(dadosNovos.getNome());
        }
        if (dadosNovos.getEmail() != null) {
            if (usuarioRepository.existsByEmail(dadosNovos.getEmail())) {
                throw new RuntimeException("Este novo e-mail já está em uso!");
            }
            usuarioExistente.setEmail(dadosNovos.getEmail());
        }
        // troca de senha removida do PATCH até existir o UsuarioUpdateRequest
        return usuarioMapper.toResponse(usuarioRepository.save(usuarioExistente));
    }

    public Usuario buscarEntidade(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }
}