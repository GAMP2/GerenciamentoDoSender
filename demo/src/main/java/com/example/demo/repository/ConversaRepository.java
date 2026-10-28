package com.example.demo.repository;

import com.example.demo.model.Conexao;
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

    Optional<Conversa> findByConexao(Conexao conexao);

    Optional<Conversa> findByConexaoId(UUID conexaoId);

    @Query("SELECT c FROM Conversa c WHERE c.conexao.usuario1 = :usuario OR c.conexao.usuario2 = :usuario")
    List<Conversa> listarDoUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT c FROM Conexao c WHERE (c.usuario1 = :u1 AND c.usuario2 = :u2) OR (c.usuario1 = :u2 AND c.usuario2 = :u1)")
    Optional<Conexao> buscarEntreUsuarios(@Param("u1") Usuario u1, @Param("u2") Usuario u2);
}