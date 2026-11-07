package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record IntegracoesExternasResponse(
        UUID id,
        UUID usuarioId,
        String provedor,
        String idExterno,
        String tokenAcesso,
        String tokenAtualizacao,
        LocalDate dataExpiracao,
        LocalDate dataCriacao
) {}