package com.example.demo.service.interacoes;

import com.example.demo.dto.request.MensagemRequest;
import com.example.demo.dto.response.MensagemResponse;
import com.example.demo.mapper.MensagemMapper;
import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Conversa;
import com.example.demo.model.interacoes.Mensagem;
import com.example.demo.repository.interacoes.BloqueioRepository;
import com.example.demo.repository.interacoes.ConversaRepository;
import com.example.demo.repository.interacoes.MensagemRepository;
import com.example.demo.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ConversaRepository conversaRepository;
    private final BloqueioRepository bloqueioRepository;
    private final UsuarioService usuarioService;
    private final MensagemMapper mensagemMapper;

    public MensagemService(MensagemRepository mensagemRepository, ConversaRepository conversaRepository, BloqueioRepository bloqueioRepository, UsuarioService usuarioService, MensagemMapper mensagemMapper) {this.mensagemRepository = mensagemRepository;this.conversaRepository = conversaRepository;this.bloqueioRepository = bloqueioRepository;this.usuarioService = usuarioService;this.mensagemMapper = mensagemMapper;
    }

    @Transactional
    public MensagemResponse enviarMensagem(MensagemRequest request) {
        boolean semTexto = request.conteudoTexto() == null || request.conteudoTexto().isBlank();
        boolean semImagem = request.urlImagem() == null || request.urlImagem().isBlank();
        if (semTexto && semImagem) {
            throw new RuntimeException("A mensagem precisa ter texto ou imagem.");
        }

        Conversa conversa = buscarConversa(request.conversaId());

        // Só quem participa da conversa pode enviar mensagem nela
        if (!conversa.temParticipante(request.remetenteId())) {
            throw new RuntimeException("O remetente não participa desta conversa.");
        }

        Usuario remetente = usuarioService.buscarEntidade(request.remetenteId());
        Usuario destinatario = conversa.getOutroParticipante(request.remetenteId());

        boolean bloqueado =
                bloqueioRepository.existsByUsuarioBloqueadorAndUsuarioBloqueado(remetente, destinatario)
                        || bloqueioRepository.existsByUsuarioBloqueadorAndUsuarioBloqueado(destinatario, remetente);
        if (bloqueado) {
            throw new RuntimeException("Não é possível enviar mensagem devido a um bloqueio ativo.");
        }

        conversa.setAtualizadoEm(LocalDateTime.now());
        conversaRepository.save(conversa);

        Mensagem mensagem = new Mensagem(conversa, remetente, request.conteudoTexto(), request.urlImagem());
        return mensagemMapper.toResponse(mensagemRepository.save(mensagem));
    }

    public List<MensagemResponse> listarMensagensDaConversa(Long conversaId) {
        Conversa conversa = buscarConversa(conversaId);
        return mensagemRepository.findByConversaOrderByEnviadoEmAsc(conversa).stream()
                .map(mensagemMapper::toResponse)
                .toList();
    }

    @Transactional
    public void marcarComoLidas(Long conversaId, Long usuarioId) {
        Conversa conversa = buscarConversa(conversaId);
        List<Mensagem> naoLidas = mensagemRepository.findByConversaAndLidoEmIsNull(conversa);
        LocalDateTime agora = LocalDateTime.now();

        for (Mensagem m : naoLidas) {
            // Marca como lida apenas as mensagens que não foram enviadas pelo próprio utilizador
            if (!m.getRemetente().getId().equals(usuarioId)) {
                m.setLidoEm(agora);
            }
            if (!conversa.temParticipante(usuarioId)) {
                throw new RuntimeException("O usuário não participa desta conversa.");
            }
        }
        mensagemRepository.saveAll(naoLidas);
    }

    private Conversa buscarConversa(Long conversaId) {
        return conversaRepository.findById(conversaId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada."));
    }
}