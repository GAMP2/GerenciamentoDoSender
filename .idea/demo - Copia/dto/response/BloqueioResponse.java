package com.example.demo.dto.response;

import java.time.LocalDateTime;

public record BloqueioResponse(
        String id,
        Long idBloqueador,
        Long idBloqueado,
        String nomeBloqueado,
        String motivo,
        LocalDateTime criadoEm
) {}