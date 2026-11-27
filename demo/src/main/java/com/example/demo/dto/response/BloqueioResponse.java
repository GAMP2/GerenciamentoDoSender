package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record BloqueioResponse(
        UUID id,
        UUID idBloqueador,
        UUID idBloqueado,
        String motivo,
        LocalDateTime criadoEm
) {}