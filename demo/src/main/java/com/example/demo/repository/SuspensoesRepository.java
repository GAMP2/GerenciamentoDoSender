package com.example.demo.repository;

import com.example.demo.entity.Suspensoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SuspensoesRepository extends JpaRepository<Suspensoes, UUID> {

    List<Suspensoes> findByUsuarioId(UUID usuarioId);
}