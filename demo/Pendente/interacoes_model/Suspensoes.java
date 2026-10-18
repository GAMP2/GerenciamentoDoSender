package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
//-----------preciso terminar
@Data
@NoArgsConstructor
public class Suspensoes {
    @Id
    @GeneratedValue
    private int id;
    private String motivo;
    private boolean status;
    private LocalDate dataSolicitacao;
    private LocalDate dataRevisao;
    private LocalDate dataEntrega;
    private String observacaoDecisao;

    public String solicitarSuspensao() {
        dataSolicitacao = LocalDate.now();
        return motivo;
    }


}
