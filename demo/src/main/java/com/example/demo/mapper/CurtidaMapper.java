package com.example.demo.mapper;

import com.example.demo.dto.response.CurtidaResponse;
import com.example.demo.entity.Curtidas;
import org.springframework.stereotype.Component;

@Component
public class CurtidaMapper {
    public CurtidaResponse toResponse(Curtidas curtida) {
        return new CurtidaResponse(
                curtida.getId(),
                curtida.getUsuarioOrigem().getId(),
                curtida.getUsuarioDestino().getId(),
                curtida.getDataCurtida()
        );
    }
}