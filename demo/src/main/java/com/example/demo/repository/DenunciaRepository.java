package com.example.demo.repository;

import com.example.demo.entity.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, UUID> {

    // Buscar todas as denúncias com um status específico :)
    List<Denuncia> findByStatus(String status);

    List<Denuncia> findByDenunciadoIdOrderByCriadaEmDesc(UUID idDenunciado);

    List<Denuncia> findByDenuncianteIdOrderByCriadaEmDesc(UUID idDenunciante);
}
