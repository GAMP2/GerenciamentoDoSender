package com.example.demo.dto.request;
import jakarta.validation.constraints.NotNull;

public record CurtidaRequest(
            @NotNull(message = "O id de quem curte é obrigatório")
            Long idOrigem,

            @NotNull(message = "O id de quem será curtido é obrigatório")
            Long idDestino
    ) {}

