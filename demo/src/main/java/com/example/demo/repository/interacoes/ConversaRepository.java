package com.example.demo.repository.interacoes;

import com.example.demo.model.interacoes.Conversa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, Long> {
}