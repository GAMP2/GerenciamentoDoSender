package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "suspensoes")
public class Suspensoes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String motivo;

    private boolean status;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    @Column(name = "data_revisao")
    private LocalDate dataRevisao;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @Column(name = "observacao_decisao")
    private String observacaoDecisao;

    public Suspensoes() {
    }

    public Suspensoes(Usuario usuario, String motivo, boolean status, LocalDate dataSolicitacao, LocalDate dataRevisao, LocalDate dataEntrega, String observacaoDecisao) {
        this.usuario = usuario;
        this.motivo = motivo;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.dataRevisao = dataRevisao;
        this.dataEntrega = dataEntrega;
        this.observacaoDecisao = observacaoDecisao;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDate getDataRevisao() {
        return dataRevisao;
    }

    public void setDataRevisao(LocalDate dataRevisao) {
        this.dataRevisao = dataRevisao;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getObservacaoDecisao() {
        return observacaoDecisao;
    }

    public void setObservacaoDecisao(String observacaoDecisao) {
        this.observacaoDecisao = observacaoDecisao;
    }
}