package com.example.demo.repository;

import com.example.demo.entity.Interesses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InteresseRepository extends JpaRepository<Interesses, UUID> {
    Optional<Interesses> findByNomeIgnoreCase(String nome);
    List<Interesses> findByNomeContainingIgnoreCase(String termo);
}