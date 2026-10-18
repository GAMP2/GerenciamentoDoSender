package com.example.demo.model.interacoes;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bloqueios_usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_bloqueador", "id_bloqueado"})
})
public class Bloqueios {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_bloqueador", nullable = false)
    private Usuario usuarioBloqueador;

    @ManyToOne
    @JoinColumn(name = "id_bloqueado", nullable = false)
    private Usuario usuarioBloqueado;

    private String motivo;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();

    // Construtor utilitário para facilitar no Service
    public Bloqueios(Usuario usuarioBloqueador, Usuario usuarioBloqueado, String motivo) {
        this.usuarioBloqueador = usuarioBloqueador;
        this.usuarioBloqueado = usuarioBloqueado;
        this.motivo = motivo;
        this.criadoEm = LocalDateTime.now();
    }
}