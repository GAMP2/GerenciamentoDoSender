package com.example.demo.dto.response;

import java.time.LocalDateTime;

public record ConversaResponse(
        Long id,
        Long idUsuario1,
        String nomeUsuario1,
        Long idUsuario2,
        String nomeUsuario2,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}