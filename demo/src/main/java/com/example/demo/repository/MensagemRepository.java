package com.example.demo.repository;

import com.example.demo.entity.Conversa;
import com.example.demo.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {

    List<Mensagem> findByConversaOrderByEnviadoEmAsc(Conversa conversa);
    // Busca todas as mensagens de uma conversa ordenadas por data/hora de envio

    List<Mensagem> findByConversaAndLidoEmIsNull(Conversa conversa);
}