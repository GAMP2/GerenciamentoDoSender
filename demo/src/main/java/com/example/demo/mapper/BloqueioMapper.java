package com.example.demo.mapper;

import com.example.demo.dto.response.BloqueioResponse;
import com.example.demo.model.Bloqueios;
import org.springframework.stereotype.Component;

@Component
public class BloqueioMapper {
    public BloqueioResponse toResponse(Bloqueios bloqueio) {
        return new BloqueioResponse(
                bloqueio.getId(),
                bloqueio.getUsuarioBloqueador().getId(),
                bloqueio.getUsuarioBloqueado().getId(),
                bloqueio.getMotivo(),
                bloqueio.getCriadoEm()
        );
    }
}