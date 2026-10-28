package com.example.demo.repository;

import com.example.demo.model.Bloqueios;
import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BloqueioRepository extends JpaRepository<Bloqueios, UUID> {

    // Confere se o Bloqueador já bloqueou o Bloqueado
    boolean existsByUsuarioBloqueadorAndUsuarioBloqueado(Usuario usuarioBloqueador, Usuario usuarioBloqueado);

    // Busca o registro para remoção (Desbloqueio)
    Optional<Bloqueios> findByUsuarioBloqueadorAndUsuarioBloqueado(Usuario usuarioBloqueador, Usuario usuarioBloqueado);

    // Lista os bloqueios realizados por um usuário
    List<Bloqueios> findByUsuarioBloqueador(Usuario usuarioBloqueador);

    // Métodos auxiliares utilizando UUIDs dos usuários
    boolean existsByUsuarioBloqueadorIdAndUsuarioBloqueadoId(UUID usuarioBloqueadorId, UUID usuarioBloqueadoId);

    void deleteByUsuarioBloqueadorAndUsuarioBloqueado(Usuario usuarioBloqueador, Usuario usuarioBloqueado);
}