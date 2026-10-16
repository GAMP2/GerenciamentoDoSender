package com.example.demo.service.interacoes;

import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Conversa;
import com.example.demo.model.interacoes.Mensagem;
import com.example.demo.repository.interacoes.BloqueioRepository;
import com.example.demo.repository.interacoes.ConversaRepository;
import com.example.demo.repository.interacoes.MensagemRepository;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private ConversaRepository conversaRepository;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private UsuarioService usuarioService;

    // Enviar Mensagem
    public Mensagem enviarMensagem(long conversaId, long remetenteId, long destinatarioId, String conteudoTexto, String urlImagem) {
        Usuario remetente = usuarioService.buscarPorId(remetenteId);
        Usuario destinatario = usuarioService.buscarPorId(destinatarioId);

        // Verifica se existe bloqueio entre os dois utilizadores
        boolean bloqueado = bloqueioRepository.existsByUsuarioBloqueadorAndUsuarioBloqueado(remetente, destinatario) ||
                bloqueioRepository.existsByUsuarioBloqueadorAndUsuarioBloqueado(destinatario, remetente);

        if (bloqueado) {
            throw new RuntimeException("Não é possível enviar mensagem devido a um bloqueio ativo.");
        }

        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada."));

        // Atualiza a data de atualização da conversa
        conversa.setAtualizadoEm(LocalDateTime.now());
        conversaRepository.save(conversa);

        Mensagem mensagem = new Mensagem(conversa, remetente, conteudoTexto, urlImagem);
        return mensagemRepository.save(mensagem);
    }

    //Buscar Mensagens de uma Conversa
    public List<Mensagem> listarMensagensDaConversa(long conversaId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada."));

        return mensagemRepository.findByConversaOrderByEnviadoEmAsc(conversa);
    }

    // Marcar Mensagens como Lidas
   public void marcarComoLidas(long conversaId, long usuarioId) {
        Conversa conversa = conversaRepository.findById(conversaId)
                .orElseThrow(() -> new RuntimeException("Conversa não encontrada."));

        List<Mensagem> mensagensNaoLidas = mensagemRepository.findByConversaAndLidoEmIsNull(conversa);

        for (Mensagem m : mensagensNaoLidas) {
            // Marca como lida apenas as mensagens que não foram enviadas pelo próprio utilizador
            if (m.getRemetente().getId() ==(usuarioId)) {
                m.setLidoEm(LocalDateTime.now());
                mensagemRepository.save(m);
            }
        }
   }
}