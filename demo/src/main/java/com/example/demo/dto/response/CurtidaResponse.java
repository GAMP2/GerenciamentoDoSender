package com.example.demo.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record CurtidaResponse(
        UUID id,
        UUID usuarioOrigemId,
        UUID usuarioDestinoId,
        LocalDate dataCurtida
) {}