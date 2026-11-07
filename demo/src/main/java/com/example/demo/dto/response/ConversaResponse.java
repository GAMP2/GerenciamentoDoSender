package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConversaResponse(
        UUID id,
        UUID conexaoId,
        UUID idUsuarioA,
        String nomeUsuarioA,
        UUID idUsuarioB,
        String nomeUsuarioB,
        LocalDateTime criadoEm
) {}