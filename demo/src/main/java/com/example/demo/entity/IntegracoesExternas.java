package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "integracoes_externas")
public class IntegracoesExternas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String provedor;

    @Column(name = "id_externo")
    private String idExterno;

    @Column(name = "token_acesso")
    private String tokenAcesso;

    @Column(name = "token_atualizacao")
    private String tokenAtualizacao;

    @Column(name = "data_expiracao")
    private LocalDate dataExpiracao;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;}