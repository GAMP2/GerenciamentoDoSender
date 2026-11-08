package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BloqueioRequest(
        @NotNull(message = "O id de quem bloqueia é obrigatório")
        Long idBloqueador,

        @NotNull(message = "O id de quem será bloqueado é obrigatório")
        Long idBloqueado,

        @Size(max = 255, message = "O motivo deve ter no máximo 255 caracteres")
        String motivo
) {}