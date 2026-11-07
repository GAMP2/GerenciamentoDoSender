package com.example.demo.repository.interacoes;

import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Curtidas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurtidasRepository extends JpaRepository<Curtidas, Long> {

    // Usado no alternarCurtida (CurtidaService) e na limpeza de curtidas ao bloquear (BloqueioService)
    Optional<Curtidas> findByUsuarioOrigemAndUsuarioDestino(Usuario usuarioOrigem, Usuario usuarioDestino);

    // Total de curtidas recebidas por um usuário
    long countByUsuarioDestino(Usuario usuarioDestino);

    // Lista as curtidas recebidas por um usuário
    List<Curtidas> findByUsuarioDestino(Usuario usuarioDestino);
}