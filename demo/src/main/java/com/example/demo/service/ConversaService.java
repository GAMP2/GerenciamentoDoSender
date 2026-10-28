package com.example.demo.service;

import com.example.demo.dto.request.ConversaRequest;
import com.example.demo.dto.response.ConversaResponse;
import com.example.demo.mapper.ConversaMapper;
import com.example.demo.model.Usuario;
import com.example.demo.model.Conversa;
import com.example.demo.repository.BloqueioRepository;
import com.example.demo.repository.ConversaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ConversaService {

    private final ConversaRepository conversaRepository;
    private final BloqueioRepository bloqueioRepository;
    private final UsuarioService usuarioService;
    private final ConversaMapper conversaMapper;

    public ConversaService(ConversaRepository conversaRepository,
                           BloqueioRepository bloqueioRepository,
                           UsuarioService usuarioService,
                           ConversaMapper conversaMapper) {
        this.conversaRepository = conversaRepository;
        this.bloqueioRepository = bloqueioRepository;
        this.usuarioService = usuarioService;
        this.conversaMapper = conversaMapper;
    }

    // Inicia uma conversa entre dois usuários (ou devolve a que já existe)
    @Transactional
    public ConversaResponse iniciarConversa(ConversaRequest request) {
        // Regra 1: não existe conversa consigo mesmo
        if (request.idUsuario1().equals(request.idUsuario2())) {
            throw new IllegalArgumentException("Não é possível iniciar uma conversa consigo mesmo.");
        }

        Usuario u1 = usuarioService.buscarEntidade(request.idUsuario1());
        Usuario u2 = usuarioService.buscarEntidade(request.idUsuario2());

        // Regra 2: não pode haver bloqueio entre os dois, em nenhum sentido
        boolean bloqueado =
                bloqueioRepository.existsByBloqueadorIdAndBloqueadoId(u1.getId(), u2.getId())
                        || bloqueioRepository.existsByBloqueadorIdAndBloqueadoId(u2.getId(), u1.getId());
        if (bloqueado) {
            throw new IllegalStateException("Não é possível iniciar conversa devido a um bloqueio ativo.");
        }

        // Regra 3: se já existe conversa entre os dois, reutiliza; senão cria
        Conversa conversa = conversaRepository.buscarEntreUsuarios(u1, u2)
                .orElseGet(() -> conversaRepository.save(new Conversa(u1, u2)));

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