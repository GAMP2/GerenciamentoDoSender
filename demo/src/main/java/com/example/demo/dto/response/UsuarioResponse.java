package com.example.demo.dto.response;

import com.example.demo.model.enums.Objetivo;
import com.example.demo.model.enums.Papel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String username,
        String email,
        int idade,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dtNasc,
        Papel papel,
        String biografia,
        Objetivo objetivo,
        Integer preferenciaMin,
        Integer preferenciaMax,
        boolean verificado,
        LocalDateTime criadoEm
) {}