package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record DenunciaRequest(
        @NotNull(message = "O ID do denunciante é obrigatório")
        UUID idDenunciante,

        @NotNull(message = "O ID do denunciado é obrigatório")
        UUID idDenunciado,

        @NotBlank(message = "O motivo da denúncia é obrigatório")
        @Size(max = 255, message = "O motivo deve ter no máximo 255 caracteres")
        String motivo,

        @Size(max = 1000, message = "Os detalhes da denúncia devem ter no máximo 1000 caracteres")
        String detalhes
) {}
