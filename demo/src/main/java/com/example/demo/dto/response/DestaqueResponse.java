package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record DestaqueResponse(
        UUID id,
        String titulo,
        String capaUrl,
        String midiaUrl,
        LocalDateTime criadoEm
) {}