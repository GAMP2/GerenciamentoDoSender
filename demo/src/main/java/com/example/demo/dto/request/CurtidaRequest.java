package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CurtidaRequest(
        @NotNull(message = "O ID de origem é obrigatório")
        UUID idOrigem,

        @NotNull(message = "O ID de destino é obrigatório")
        UUID idDestino
) {}