package com.example.demo.service;

import com.example.demo.dto.response.MensagemResponse;
import com.example.demo.mapper.MensagemMapper;
import com.example.demo.entity.Conversa;
import com.example.demo.entity.Mensagem;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.ConversaRepository;
import com.example.demo.repository.MensagemRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ConversaRepository conversaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MensagemMapper mensagemMapper;

    public MensagemService(MensagemRepository mensagemRepository,
                           ConversaRepository conversaRepository,
                           UsuarioRepository usuarioRepository,
                           MensagemMapper mensagemMapper) {
        this.mensagemRepository = mensagemRepository;
        this.conversaRepository = conversaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mensagemMapper = mensagemMapper;
    }
    //@Transactional(readOnly = true): Avisa ao banco de dados que este
    // método é apenas leitura. Isso deixa a consulta mais rápida e otimizada.
    @Transactional(readOnly = true)
    public List<MensagemResponse> listarPorConversa(UUID conversaId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new IllegalArgumentException("Conversa não encontrada com o ID fornecido"));
        return mensagemRepository.findByConversaOrderByEnviadoEmAsc(conversa)
                .stream()
                .map(mensagemMapper::toResponse)
                .toList();
    }
    ///Garante que a operação ocorra dentro de uma transação do banco. Se acontecer qualquer erro durante o processo,
    /// o Spring desfaz a operação (rollback) para não salvar dados incompletos.
    @Transactional
    public MensagemResponse enviarMensagem(UUID conversaId, UUID remetenteId, String conteudo) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new IllegalArgumentException("Conversa não encontrada com o ID fornecido"));
        Usuario remetente = usuarioRepository.findById(remetenteId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID fornecido"));

        Mensagem mensagem = new Mensagem();
        Mensagem mensagemSalva = mensagemRepository.save(mensagem);

        return mensagemMapper.toResponse(mensagemSalva);
    }
    @Transactional
    public void marcarComoLidas(UUID conversaId, UUID usuarioId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new IllegalArgumentException("Conversa não encontrada"));

        // 1. Busca todas as mensagens não lidas da conversa
        List<Mensagem> mensagensNaoLidas = mensagemRepository.findByConversaAndLidoEmIsNull(conversa);

        // 2. Filtra para alterar apenas as mensagens enviadas por OUTRO usuário (não pelo próprio leitor)
        LocalDateTime agora = LocalDateTime.now();
        mensagensNaoLidas.stream()
                .filter(mensagem -> !mensagem.getRemetente().getId().equals(usuarioId))
                .forEach(mensagem -> mensagem.setLidoEm(agora));

        // O Spring salva automaticamente ao final do @Transactional (Dirty Checking),
        // ou você pode chamar mensagemRepository.saveAll(mensagensNaoLidas);
    }
}