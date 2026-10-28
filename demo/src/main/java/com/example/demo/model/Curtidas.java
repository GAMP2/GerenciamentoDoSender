package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "curtidas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_origem_id", "usuario_destino_id"})
})
public class Curtidas {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_origem_id", nullable = false)
    private Usuario usuarioOrigem;

    @ManyToOne
    @JoinColumn(name = "usuario_destino_id", nullable = false)
    private Usuario usuarioDestino;

    private LocalDate dataCurtida = LocalDate.now();

    public Curtidas(Usuario usuarioOrigem, Usuario usuarioDestino) {
        this.usuarioOrigem = usuarioOrigem;
        this.usuarioDestino = usuarioDestino;
        this.dataCurtida = LocalDate.now();
    }
}