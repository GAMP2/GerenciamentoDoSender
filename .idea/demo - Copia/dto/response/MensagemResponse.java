package com.example.demo.dto.response;

import java.time.LocalDateTime;

public record MensagemResponse(
        Long id,
        Long conversaId,
        Long remetenteId,
        String nomeRemetente,
        String conteudoTexto,
        String urlImagem,
        LocalDateTime enviadoEm,
        LocalDateTime lidoEm
) {}