package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record MensagemResponse(
        UUID id,
        UUID conversaId,
        UUID remetenteId,
        String nomeRemetente,
        String conteudoTexto,
        String urlImagem,
        LocalDateTime enviadoEm,
        LocalDateTime lidoEm
) {}