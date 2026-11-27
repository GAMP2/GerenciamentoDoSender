package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DestaqueRequest(
        @NotBlank(message = "O título do destaque é obrigatório")
        @Size(max = 30, message = "O título deve ter no máximo 30 caracteres")
        String titulo,

        @NotBlank(message = "A URL da capa é obrigatória")
        String capaUrl,

        @NotBlank(message = "A URL da mídia é obrigatória")
        String midiaUrl
) {}