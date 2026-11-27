package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record AtualizarInteressesRequest(
        @NotNull(message = "O ID do usuário é obrigatório")
        UUID usuarioId,

        @NotNull(message = "A lista de interesses não pode ser nula")
        @Size(max = 5, message = "É permitido selecionar no máximo 5 interesses")
        List<UUID> interesseIds
) {}