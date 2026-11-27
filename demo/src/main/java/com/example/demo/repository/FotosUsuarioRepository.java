package com.example.demo.repository;

import com.example.demo.entity.FotosUsuario;
import com.example.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FotosUsuarioRepository extends JpaRepository<FotosUsuario, UUID> {

    // Busca todas as fotos cadastradas para um determinado usuário
    List<FotosUsuario> findByUsuario(Usuario usuario);

    // Busca fotos diretamente pelo ID do usuário
    List<FotosUsuario> findByUsuarioId(UUID usuarioId);
}