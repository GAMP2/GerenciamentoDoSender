package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bloqueios_usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_bloqueador", "id_bloqueado"})
})
public class Bloqueios {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_bloqueador", nullable = false)
    private Usuario usuarioBloqueador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_bloqueado", nullable = false)
    private Usuario usuarioBloqueado;

    private String motivo;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    public Bloqueios(Usuario usuarioBloqueador, Usuario usuarioBloqueado, String motivo) {
        this.usuarioBloqueador = usuarioBloqueador;
        this.usuarioBloqueado = usuarioBloqueado;
        this.motivo = motivo;
    }
}