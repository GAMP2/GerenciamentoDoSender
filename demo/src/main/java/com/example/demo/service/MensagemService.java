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
                .toList();}
        @Transactional
        public MensagemResponse enviarMensagem(UUID conversaId, UUID remetenteId, String conteudo) {
            Conversa conversa = conversaRepository.findById(conversaId)
                    .orElseThrow(() -> new IllegalArgumentException("Conversa não encontrada com o ID fornecido"));
            Usuario remetente = usuarioRepository.findById(remetenteId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID fornecido"));

            Mensagem mensagem = new Mensagem();
            mensagem.setConversa(conversa);
            mensagem.setRemetente(remetente);
            mensagem.setConteudo(conteudo);
            mensagem.setEnviadoEm(LocalDateTime.now());

            Mensagem mensagemSalva = mensagemRepository.save(mensagem);

            return mensagemMapper.toResponse(mensagemSalva);
        }

        @Transactional
    public void marcarComoLidas(UUID conversaId, UUID usuarioId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new IllegalArgumentException("Conversa não encontrada"));

        List<Mensagem> mensagensNaoLidas = mensagemRepository.findByConversaAndLidoEmIsNull(conversa);

        LocalDateTime agora = LocalDateTime.now();
        mensagensNaoLidas.stream()
                .filter(mensagem -> !mensagem.getRemetente().getId().equals(usuarioId))
                .forEach(mensagem -> mensagem.setLidoEm(agora));
    }
    @Transactional
    public void deletarMensagem(UUID id) {
        if (!mensagemRepository.existsById(id)) {
            throw new IllegalArgumentException("Mensagem não encontrada com o ID fornecido");
        }
        mensagemRepository.deleteById(id);
    }

}