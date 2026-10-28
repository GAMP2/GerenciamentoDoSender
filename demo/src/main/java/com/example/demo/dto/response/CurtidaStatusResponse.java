package com.example.demo.dto.response;

public record CurtidaStatusResponse(
        boolean curtido,
        String mensagem
) {}