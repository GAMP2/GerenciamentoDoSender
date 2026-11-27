package com.example.demo.repository;

import com.example.demo.entity.Conexao;
import com.example.demo.entity.Conversa;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversaRepository extends JpaRepository<Conversa, UUID> {

    Optional<Conversa> findByConexao(Conexao conexao);

    Optional<Conversa> findByConexaoId(UUID conexaoId);

    List<Conversa> findByConexaoUsuarioAOrConexaoUsuarioB(Usuario usuarioA, Usuario usuarioB);
}