package com.example.demo.service;

import com.example.demo.dto.request.ConversaRequest;
import com.example.demo.dto.response.ConversaResponse;
import com.example.demo.mapper.ConversaMapper;
import com.example.demo.entity.Conexao;
import com.example.demo.entity.Conversa;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.BloqueioRepository;
import com.example.demo.repository.ConexaoRepository;
import com.example.demo.repository.ConversaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ConversaService {

    private final ConversaRepository conversaRepository;
    private final ConexaoRepository conexaoRepository;
    private final BloqueioRepository bloqueioRepository;
    private final UsuarioService usuarioService;
    private final ConversaMapper conversaMapper;

    public ConversaService(ConversaRepository conversaRepository,
                           ConexaoRepository conexaoRepository,
                           BloqueioRepository bloqueioRepository,
                           UsuarioService usuarioService,
                           ConversaMapper conversaMapper) {
        this.conversaRepository = conversaRepository;
        this.conexaoRepository = conexaoRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.usuarioService = usuarioService;
        this.conversaMapper = conversaMapper;
    }

    @Transactional
    public ConversaResponse iniciarConversa(ConversaRequest request) {
        if (request.idUsuario1().equals(request.idUsuario2())) {
            throw new IllegalArgumentException("Não é possível iniciar uma conversa consigo mesmo.");
        }

        Usuario u1 = usuarioService.buscarEntidade(request.idUsuario1());
        Usuario u2 = usuarioService.buscarEntidade(request.idUsuario2());

        boolean bloqueado = bloqueioRepository.existsByUsuarioBloqueadorAndUsuarioBloqueado(u1, u2)
                || bloqueioRepository.existsByUsuarioBloqueadorAndUsuarioBloqueado(u2, u1);

        if (bloqueado) {
            throw new IllegalStateException("Não é possível iniciar conversa devido a um bloqueio ativo.");
        }

        Conexao conexao = conexaoRepository.buscarEntreUsuarios(u1, u2)
                .orElseThrow(() -> new RuntimeException("Conexão entre os usuários não encontrada."));

        Conversa conversa = conversaRepository.findByConexao(conexao)
                .orElseGet(() -> conversaRepository.save(new Conversa(conexao)));

        return conversaMapper.toResponse(conversa);
    }

    @Transactional(readOnly = true)
    public ConversaResponse buscarPorId(UUID id) {
        Conversa conversa = conversaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada."));
        return conversaMapper.toResponse(conversa);
    }

    @Transactional(readOnly = true)
    public List<ConversaResponse> listarConversasDoUsuario(UUID usuarioId) {
        Usuario usuario = usuarioService.buscarEntidade(usuarioId);
        return conversaRepository.listarDoUsuario(usuario).stream()
                .map(conversaMapper::toResponse)
                .toList();
    }
}