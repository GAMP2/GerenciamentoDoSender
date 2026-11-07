package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;

public record ConversaRequest(
        @NotNull(message = "O primeiro usuário é obrigatório")
        Long idUsuario1,

        @NotNull(message = "O segundo usuário é obrigatório")
        Long idUsuario2
) {}