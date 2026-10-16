package com.example.demo.repository.interacoes;

import com.example.demo.model.interacoes.Conversa;
import com.example.demo.model.interacoes.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    // Lista todas as mensagens de uma conversa em ordem cronológica
    List<Mensagem> findByConversaOrderByEnviadoEmAsc(Conversa conversa);

    // Lista mensagens não lidas de uma conversa
    List<Mensagem> findByConversaAndLidoEmIsNull(Conversa conversa);
}