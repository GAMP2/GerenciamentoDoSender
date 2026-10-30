package com.example.demo.model.interacoes;

import com.example.demo.model.Usuario;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class IntegracoesExternas {
    @GeneratedValue
    @Id
    private long id;
    private LocalDate usuarioId;
    private String provedor;
    private long id_externo;
    private String tokenAcesso;
    private String tokenAtualizacao;
    private LocalDate dataExpiracao;
    private LocalDate dataCriacao;

    IntegracoesExternas(Usuario usuario) {

    }
}
