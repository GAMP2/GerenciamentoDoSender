package com.example.demo.dto.response;

import com.example.demo.model.enums.Genero;
import com.example.demo.model.enums.Objetivo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UsuarioResponse(
        long id,
        String nome,
        String email,
        int idade,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dtNasc,
        String biografia,
        int preferenciaMin,
        int preferenciaMax,
        boolean verificado,
        Objetivo objetivo,
        Genero genero,
        LocalDate dataCriacao
) {}