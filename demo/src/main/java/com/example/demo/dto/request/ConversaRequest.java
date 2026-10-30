package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ConversaRequest(
        @NotNull(message = "O ID do primeiro usuário é obrigatório")
        UUID idUsuario1,

        @NotNull(message = "O ID do segundo usuário é obrigatório")
        UUID idUsuario2


) {}