package com.example.demo.repository;

import com.example.demo.model.Conexao;
import com.example.demo.model.Usuario;
import com.example.demo.model.enums.StatusConexao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConexaoRepository extends JpaRepository<Conexao, UUID> {

    // Existe uma conexão com esse status entre A e B?
    Optional<Conexao> findByUsuarioAAndUsuarioBAndStatus(Usuario usuarioA, Usuario usuarioB, StatusConexao status);
}