package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record BloqueioRequest(
        @NotNull(message = "O ID do usuário bloqueador é obrigatório")
        UUID idBloqueador,

        @NotNull(message = "O ID do usuário bloqueado é obrigatório")
        UUID idBloqueado,

        String motivo
) {}