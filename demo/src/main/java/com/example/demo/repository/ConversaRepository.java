package com.example.demo.repository;

import com.example.demo.model.Conversa;
import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, UUID> {

    @Query("SELECT c FROM Conversa c WHERE (c.usuario1 = :u1 AND c.usuario2 = :u2) OR (c.usuario1 = :u2 AND c.usuario2 = :u1)")
    Optional<Conversa> buscarEntreUsuarios(@Param("u1") Usuario u1, @Param("u2") Usuario u2);

    @Query("SELECT c FROM Conversa c WHERE c.usuario1 = :usuario OR c.usuario2 = :usuario")
    List<Conversa> listarDoUsuario(@Param("usuario") Usuario usuario);
}