package com.example.demo.entity;

import com.example.demo.entity.enums.StatusConexao;
import com.example.demo.entity.enums.TipoConexao;
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
@Table(name = "conexoes")
public class Conexao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoConexao tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_a_id", nullable = false)
    private Usuario usuarioA;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_b_id", nullable = false)
    private Usuario usuarioB;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConexao status = StatusConexao.ATIVA;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "encerrado_em")
    private LocalDateTime encerradoEm;
}