package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "denuncias")
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_denunciante", nullable = false)
    private Usuario denunciante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_denunciado", nullable = false)
    private Usuario denunciado;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String motivo;

    @Column(columnDefinition = "TEXT")
    private String detalhes;

    @CreationTimestamp
    @Column(name = "criada_em", nullable = false, updatable = false)
    private LocalDateTime criadaEm;

    @Column(nullable = false, length = 20)
    private String status = "PENDENTE";
}
