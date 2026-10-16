package com.example.demo.model.interacoes;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "mensagens")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "conversa_id", nullable = false)
    private Conversa conversa;

    @ManyToOne
    @JoinColumn(name = "id_remetente", nullable = false)
    private Usuario remetente;

    @Column(name = "conteudo_texto", columnDefinition = "TEXT")
    private String conteudoTexto;

    @Column(name = "url_imagem", length = 500)
    private String urlImagem;

    @Column(name = "enviado_em", nullable = false)
    private LocalDateTime enviadoEm = LocalDateTime.now();

    @Column(name = "lido_em")
    private LocalDateTime lidoEm;

    // Construtor utilitário
    public Mensagem(Conversa conversa, Usuario remetente, String conteudoTexto, String urlImagem) {
        this.conversa = conversa;
        this.remetente = remetente;
        this.conteudoTexto = conteudoTexto;
        this.urlImagem = urlImagem;
        this.enviadoEm = LocalDateTime.now();
    }
}