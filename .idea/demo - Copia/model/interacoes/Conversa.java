package com.example.demo.model.interacoes;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "conversas")
public class Conversa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario1_id", nullable = false)
    private Usuario usuario1;

    @ManyToOne
    @JoinColumn(name = "usuario2_id", nullable = false)
    private Usuario usuario2;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm = LocalDateTime.now();

    public Conversa(Usuario usuario1, Usuario usuario2) {
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
    }

    // Esta pessoa participa da conversa?
    public boolean temParticipante(Long usuarioId) {
        return usuario1.getId().equals(usuarioId) || usuario2.getId().equals(usuarioId);
    }

    // Dado um participante, quem é o outro?
    public Usuario getOutroParticipante(Long usuarioId) {
        return usuario1.getId().equals(usuarioId) ? usuario2 : usuario1;
    }
}