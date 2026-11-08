package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record DenunciaResponse(
        UUID id,
        UUID idDenunciante,
        String nomeDenunciante,
        UUID idDenunciado,
        String nomeDenunciado,
        String motivo,
        String detalhes,
        String status,
        LocalDateTime criadaEm
) {}
