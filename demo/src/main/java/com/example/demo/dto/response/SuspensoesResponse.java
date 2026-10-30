package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record SuspensoesResponse(
        UUID id,
        UUID usuarioId,
        String motivo,
        boolean status,
        LocalDate dataSolicitacao,
        LocalDate dataRevisao,
        LocalDate dataEntrega,
        String observacaoDecisao
) {}