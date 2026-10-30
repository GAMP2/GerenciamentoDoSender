package com.example.demo.repository;

import com.example.demo.entity.IntegracoesExternas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IntegracoesExternasRepository extends JpaRepository<IntegracoesExternas, UUID> {

    List<IntegracoesExternas> findByUsuarioId(UUID usuarioId);

    Optional<IntegracoesExternas> findByUsuarioIdAndProvedor(UUID usuarioId, String provedor);
}