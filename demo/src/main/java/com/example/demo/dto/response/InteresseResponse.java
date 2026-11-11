package com.example.demo.dto.response;

import java.util.UUID;

public record InteresseResponse(
    UUID id,
    String nome,
    String icone
    ){}

