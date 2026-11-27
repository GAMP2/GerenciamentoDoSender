package com.example.demo.dto.response;

import java.util.UUID;

public record FotosUsuarioResponse(
        UUID id,
        UUID usuarioId,
        String fotoUrl
) {}