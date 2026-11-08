package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record IntegracoesExternasRequest(
        @NotNull(message = "O ID do usuário é obrigatório")
        UUID usuarioId,

        @NotBlank(message = "O provedor é obrigatório")
        String provedor,

        String idExterno,
        String tokenAcesso,
        String tokenAtualizacao,
        LocalDate dataExpiracao
) {}