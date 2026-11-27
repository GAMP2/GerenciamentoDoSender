package com.example.demo.repository;

import com.example.demo.entity.DestaquePerfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DestaquePerfilRepository extends JpaRepository<DestaquePerfil, UUID> {
    List<DestaquePerfil> findByUsuarioIdOrderByCriadoEmAsc(UUID usuarioId);
}