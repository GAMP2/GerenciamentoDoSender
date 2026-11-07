package com.example.demo.entity;

import com.example.demo.entity.enums.Objetivo;
import com.example.demo.entity.enums.Papel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, updatable = false, nullable = false)
    private UUID id;
    //tipo que está no banco
    //@Column(nullable = false, ...)	Obrigatório: não pode ficar vazio

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dtNasc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Papel papel;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    @Enumerated(EnumType.STRING)
    private Objetivo objetivo;

    @Column(name = "idade_pref_min")
    private Integer preferenciaMin;

    @Column(name = "idade_pref_max")
    private Integer preferenciaMax;

    @Column(name = "oculto_feed", nullable = false)
    private boolean ocultoFeed;

    private boolean verificado;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @Column(name = "deletado_em")
    private LocalDateTime deletadoEm;
}