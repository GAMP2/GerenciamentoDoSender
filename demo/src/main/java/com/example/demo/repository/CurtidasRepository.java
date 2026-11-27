package com.example.demo.repository;

import com.example.demo.entity.Curtidas;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurtidasRepository extends JpaRepository<Curtidas, UUID> {

    Optional<Curtidas> findByUsuarioOrigemAndUsuarioDestino(Usuario usuarioOrigem, Usuario usuarioDestino);

    boolean existsByUsuarioOrigemAndUsuarioDestino(Usuario usuarioOrigem, Usuario usuarioDestino);

    List<Curtidas> findByUsuarioDestino(Usuario usuarioDestino);

    List<Curtidas> findByUsuarioOrigem(Usuario usuarioOrigem);

    long countByUsuarioDestino(Usuario usuarioDestino);
}