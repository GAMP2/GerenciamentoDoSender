package com.example.demo.repository;

import com.example.demo.entity.Conexao;
import com.example.demo.entity.Usuario;
import com.example.demo.entity.enums.StatusConexao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConexaoRepository extends JpaRepository<Conexao, UUID> {

    // Existe uma conexão com esse status entre A e B?
    Optional<Conexao> findByUsuarioAAndUsuarioBAndStatus(Usuario usuarioA, Usuario usuarioB, StatusConexao status);

    @Query("SELECT c FROM Conexao c WHERE (c.usuarioA = :u1 AND c.usuarioB = :u2) OR (c.usuarioA = :u2 AND c.usuarioB = :u1)")
    Optional<Conexao> buscarEntreUsuarios(@Param("u1") Usuario u1, @Param("u2") Usuario u2);
}
