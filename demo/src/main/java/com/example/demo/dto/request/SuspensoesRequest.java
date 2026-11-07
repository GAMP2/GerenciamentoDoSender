package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record SuspensoesRequest(
        @NotNull(message = "O ID do usuário é obrigatório")
        UUID usuarioId,

        @NotBlank(message = "O motivo é obrigatório")
        String motivo,

        LocalDate dataRevisao,
        LocalDate dataEntrega,
        String observacaoDecisao
) {}