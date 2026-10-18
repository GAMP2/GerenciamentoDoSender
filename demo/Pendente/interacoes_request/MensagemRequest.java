package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MensagemRequest(
        @NotNull(message = "A conversa é obrigatória")
        Long conversaId,

        @NotNull(message = "O remetente é obrigatório")
        Long remetenteId,


        String conteudoTexto,

        @Size(max = 500, message = "A URL da imagem deve ter no máximo 500 caracteres")
        String urlImagem
) {}