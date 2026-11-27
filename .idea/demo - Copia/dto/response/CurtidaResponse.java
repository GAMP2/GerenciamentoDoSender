package com.example.demo.dto.response;
import java.time.LocalDate;

public record CurtidaResponse(
        Long id,
        Long idOrigem,
        Long idDestino,
        String nomeOrigem,
        LocalDate dataCurtida
) {}
