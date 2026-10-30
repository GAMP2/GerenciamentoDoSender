package com.example.demo.repository.interacoes;

import com.example.demo.model.Usuario;
import com.example.demo.model.interacoes.Bloqueios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloqueioRepository extends JpaRepository<Bloqueios, Long> {

    // Confere se o Bloqueador já bloqueou o Bloqueado
    boolean existsByUsuarioBloqueadorAndUsuarioBloqueado(Usuario usuarioBloqueador, Usuario usuarioBloqueado);

    //  Busca o registro para remoção (Desbloqueio)
    Optional<Bloqueios> findByUsuarioBloqueadorAndUsuarioBloqueado(Usuario usuarioBloqueador, Usuario usuarioBloqueado);

    // Lista os bloqueios realizados por um usuário
    List<Bloqueios> findByUsuarioBloqueador(Usuario usuarioBloqueador);
}